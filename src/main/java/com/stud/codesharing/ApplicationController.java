package com.stud.codesharing;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ApplicationController {
    Code currentCodeSnippet = new Code("", "");

    @GetMapping(value = "/code")
    public ModelAndView getAsHtml(Model model) {
        model.addAttribute("code_obj", currentCodeSnippet);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("result");
        return modelAndView;
    }

    @GetMapping("/api/code")
    @ResponseBody
    public Map<String, String> getAsJson(){
        Map<String, String> responseMap = new HashMap<>();
        responseMap.put("code", currentCodeSnippet.getCode());
        responseMap.put("date", currentCodeSnippet.getDate());

        return responseMap;
    }

    @GetMapping("/code/new")
    @ResponseBody
    public ModelAndView createNewCodeInHtml() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @PostMapping("/api/code/new")
    @ResponseBody
    public Map<String, String> postNewCode(@RequestBody Code code) {
        currentCodeSnippet = code;
        currentCodeSnippet.setDate();
        return new HashMap<>();
    }

}
