package com.stud.codesharing;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@org.springframework.stereotype.Controller
public class ApplicationController {
    List<Code> list = new ArrayList<>();

    @GetMapping("/api/code/{N}")
    @ResponseBody
    public Map<String, String> getAsJson(@PathVariable int N){
        Map<String, String> responseMap = new HashMap<>();
        if (N < list.size()) {
            Code currentCodeSnippet = list.get(N);
            if (currentCodeSnippet != null) {
                responseMap.put("code", currentCodeSnippet.getCode());
                responseMap.put("date", currentCodeSnippet.getDate());
                return responseMap;
            }
        }
        responseMap.put("code", "");
        responseMap.put("date", "");
        return responseMap;
    }

    @GetMapping(value = "/code/{N}")
    public String getAsHtml(@PathVariable int N, Model model) {
        if (N < list.size()) {
            Code currentCodeSnippet = list.get(N);
            if (currentCodeSnippet != null) {
                model.addAttribute("code_obj", currentCodeSnippet);
                return "result";
            }
        }

        Code nullCode = new Code("", "");
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

        list.add(code);
        Map<String, String> resultToReturn = new HashMap<>();
        resultToReturn.put("id", String.valueOf(list.indexOf(code)));
        return resultToReturn;
    }

    private List<Code> getLatestSnippets() {
        List<Code> currentList = new ArrayList<>(list);

        Comparator<Code> comparator = Comparator.comparing(Code::getNotFormattedDate);
        currentList.sort(comparator);
        Collections.reverse(currentList);

        return currentList.stream().limit(10).collect(Collectors.toList());
    }

}
