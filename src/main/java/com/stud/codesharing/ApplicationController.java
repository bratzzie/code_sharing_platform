package com.stud.codesharing;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ApplicationController {
    @GetMapping(value = "/code", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String getAsHtml() {
        return "<html>\n" + "<head><title>Code</title></head>\n" +
                "<body>\n" + "<pre>\n" + "public static void main(String[] args) {\n" +
                "    SpringApplication.run(CodeSharingPlatform.class, args);\n" +
                "}" + "</pre>\n" + "</body>\n" + "<m/html>";
    }
    @GetMapping("/api/code")
    @ResponseBody
    public Map<String, String> getAsJson(){
        Map<String, String> responseMap = new HashMap<>();
        responseMap.put("code", "public static void main(String[] args) {\n    SpringApplication.run(CodeSharingPlatform.class, args);\n}");
        return responseMap;
    }
}
