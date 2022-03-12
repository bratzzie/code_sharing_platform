package com.stud.codesharing;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Table(name = "codes")
@Entity
public class Code {
    @Column
    private String code;
    @Column
    private String date;
    private final String DATE_FORMATTER= "yyyy-MM-dd HH:mm:ss";
    private LocalDateTime notFormattedDate;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

    public Code(String codeSnippet, String timeInFormat, Long id) {
        code = codeSnippet;
        date = timeInFormat;
        this.notFormattedDate = LocalDateTime.now();
        this.id = id;
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

    @JsonIgnore
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
