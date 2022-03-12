package com.stud.codesharing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@org.springframework.stereotype.Controller
public class ApplicationController {

    @Autowired
    private CodeSnippetRepository repository;

    @GetMapping("/api/code/{N}")
    @ResponseBody
    public Map<String, String> getAsJson(@PathVariable Long N){
        Optional<Code> currentSnippet = repository.findById(N);

        Map<String, String> responseMap = new HashMap<>();

        if (currentSnippet.isPresent()) {
            responseMap.put("code", currentSnippet.get().getCode());
            responseMap.put("date", currentSnippet.get().getDate());
            return responseMap;
        }

        responseMap.put("code", "");
        responseMap.put("date", "");
        return responseMap;
    }

    @GetMapping(value = "/code/{N}")
    public String getAsHtml(@PathVariable Long N, Model model) {
        Optional<Code> currentSnippet = repository.findById(N);

        if (currentSnippet.isPresent()) {
            model.addAttribute("code_obj", currentSnippet.get());
            return "result";
        }

        Code nullCode = new Code("", "", 99999L);
        model.addAttribute("code_obj", nullCode);
        return "result";
    }

    @GetMapping("/code/new")
    public String createNewCodeInHtml() {
        return "index";
    }

    @GetMapping("/api/code/latest")
    @ResponseBody
    public List<Code> showLatestAsJson(){
        return getLatestSnippets();
    }

    @GetMapping("/code/latest")
    public String showLatestInHtml(Model model) {
        List<Code> currentList = getLatestSnippets();
        model.addAttribute("latestSnippets", currentList);
        return "latestResult";
    }

    @PostMapping("/api/code/new")
    @ResponseBody
    public Map<String, String> postNewCode(@RequestBody Code code) {
        code.setNotFormattedDate();
        code.setDate();

        Code savedCode = repository.save(code);
        Map<String, String> resultToReturn = new HashMap<>();
        resultToReturn.put("id", String.valueOf(savedCode.getId()));
        return resultToReturn;
    }

    private List<Code> getLatestSnippets() {
        Comparator<Code> comparator = Comparator.comparing(Code::getNotFormattedDate).reversed();

        return StreamSupport.stream(repository.findAll().spliterator(), false)
                .sorted(comparator)
                .limit(10).collect(Collectors.toList());
    }

}
