package com.stud.codesharing;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Code {
    private String code;
    private String date;
    private final String DATE_FORMATTER= "yyyy-MM-dd HH:mm:ss";
    private LocalDateTime notFormattedDate;


    public Code(String codeSnippet, String timeInFormat) {
        code = codeSnippet;
        date = timeInFormat;
        this.notFormattedDate = LocalDateTime.now();
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
        LocalDateTime uploadedTime = getNotFormattedDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMATTER);
        date = uploadedTime.format(formatter);
    }

    @JsonIgnore
    public LocalDateTime getNotFormattedDate() {
        return notFormattedDate;
    }

    public void setNotFormattedDate() {
        this.notFormattedDate = LocalDateTime.now();
    }
}
