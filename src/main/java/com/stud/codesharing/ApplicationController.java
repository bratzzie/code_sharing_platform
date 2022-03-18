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

    @Autowired
    private CodeService service;

    @GetMapping("/api/code/{UUID}")
    @ResponseBody
    public Code getAsJson(@PathVariable String UUID){
        Optional<Code> currentSnippet = repository.findById(UUID);

        if (currentSnippet.isPresent()) {
            if (!currentSnippet.get().getTimeRestricted() && !currentSnippet.get().getViewsRestricted()) {
                return currentSnippet.get();
            }
            else if (currentSnippet.get().getTimeRestricted() && currentSnippet.get().getViewsRestricted()) {
                Code codeSnippet = service.deleteCodeByIdAndViewsRestrictedAndViewsIsLessThanEqualAndTimeRestrictedAndTimeLessThanEqual(currentSnippet.get().getId());
                if (codeSnippet != null)
                    throw new CodeSnippetIllegalAccessException();


                currentSnippet.get().setViews(currentSnippet.get().getViews() - 1);
                currentSnippet.get().setViewsRestricted(true);
                repository.save(currentSnippet.get());

                return currentSnippet.get();

            } else if (currentSnippet.get().getTimeRestricted()) {
                Code codeSnippet = service.deleteCodeByIdAndTimeRestrictedAndTimeIsLessThanEqual(currentSnippet.get().getId());
                if (codeSnippet != null)
                    throw new CodeSnippetIllegalAccessException();

                return currentSnippet.get();
            } else if (currentSnippet.get().getViewsRestricted()) {
                Code codeSnippet = service.deleteCodeByIdAndViewsRestrictedAndViewsIsLessThanEqual(currentSnippet.get().getId());
                if (codeSnippet != null)
                    throw new CodeSnippetIllegalAccessException();

                currentSnippet.get().setViews(currentSnippet.get().getViews() - 1);
                currentSnippet.get().setViewsRestricted(true);
                repository.save(currentSnippet.get());

                return currentSnippet.get();
            }
        }
        throw new CodeSnippetIllegalAccessException();
    }

    @GetMapping(value = "/code/{UUID}")
    public String getAsHtml(@PathVariable String UUID, Model model) {
        Optional<Code> currentSnippet = repository.findById(UUID);

        if (currentSnippet.isPresent()) {
            if (!currentSnippet.get().getTimeRestricted() && !currentSnippet.get().getViewsRestricted()) {
                model.addAttribute("code_obj", currentSnippet.get());
                return "result";
            }
            else if (currentSnippet.get().getTimeRestricted() && currentSnippet.get().getViewsRestricted()) {
                Code codeSnippet = service.deleteCodeByIdAndViewsRestrictedAndViewsIsLessThanEqualAndTimeRestrictedAndTimeLessThanEqual(currentSnippet.get().getId());
                if (codeSnippet != null)
                    throw new CodeSnippetIllegalAccessException();


                currentSnippet.get().setViews(currentSnippet.get().getViews() - 1);
                currentSnippet.get().setViewsRestricted(true);
                repository.save(currentSnippet.get());

                model.addAttribute("code_obj", currentSnippet.get());
                return "result";

            } else if (currentSnippet.get().getTimeRestricted()) {
                Code codeSnippet = service.deleteCodeByIdAndTimeRestrictedAndTimeIsLessThanEqual(currentSnippet.get().getId());
                if (codeSnippet != null)
                    throw new CodeSnippetIllegalAccessException();


                model.addAttribute("code_obj", currentSnippet.get());
                return "result";
            } else if (currentSnippet.get().getViewsRestricted()) {
                Code codeSnippet = service.deleteCodeByIdAndViewsRestrictedAndViewsIsLessThanEqual(currentSnippet.get().getId());
                if (codeSnippet != null)
                    throw new CodeSnippetIllegalAccessException();


                Code copy = new Code();
                copy.setCode(currentSnippet.get().getCode());
                copy.setDate(currentSnippet.get().getDate());
                copy.setViews(currentSnippet.get().getViews() - 1);
                copy.setViewsRestricted(true);
                copy.setTime(currentSnippet.get().getTime());

                model.addAttribute("code_obj", copy);

                currentSnippet.get().setViews(currentSnippet.get().getViews() - 1);
                currentSnippet.get().setViewsRestricted(true);
                repository.save(currentSnippet.get());

                return "result";
            }
        }

        throw new CodeSnippetIllegalAccessException();
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
    public Map<String, String> postNewCode(@RequestBody CodeToSave code) {
        Code currentCode = new Code();
        currentCode.setCode(code.getCode());
        currentCode.setViews(code.getViews());
        currentCode.setId();
        currentCode.setNotFormattedDate();
        currentCode.setDate();
        currentCode.setTime(code.getTime());

        currentCode.setDateToDeleteSnippet();
        repository.save(currentCode);
        System.out.println("from post");
        System.out.println(currentCode.getViewsRestricted());
        System.out.println(currentCode.getId());
        Map<String, String> resultToReturn = new HashMap<>();
        resultToReturn.put("id", currentCode.getId());
        return resultToReturn;
    }

    private List<Code> getLatestSnippets() {
        Comparator<Code> comparator = Comparator.comparing(Code::getNotFormattedDate).reversed();

        return StreamSupport.stream(repository.findAll().spliterator(), false)
                .sorted(comparator)
                .filter(n -> !n.getViewsRestricted() && !n.getTimeRestricted())
                .limit(10).collect(Collectors.toList());
    }

}
