package com.stud.codesharing;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Code is inaccessible")
public class CodeSnippetIllegalAccessException extends RuntimeException{
}
