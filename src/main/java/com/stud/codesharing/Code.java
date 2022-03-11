package com.stud.codesharing;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Code {
    private String code;
    private String date;
    private final String DATE_FORMATTER= "yyyy-MM-dd HH:mm:ss";


    public Code(String codeSnippet, String timeInFormat) {
        code = codeSnippet;
        date = timeInFormat;
    }

    public Code() {
    }

    public Code(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String codeSnippet) {
        code = codeSnippet;
    }

    public String getDate() {
        return date;
    }

    public void setDate() {
        LocalDateTime uploadedTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMATTER);
        date = uploadedTime.format(formatter);
    }
}
