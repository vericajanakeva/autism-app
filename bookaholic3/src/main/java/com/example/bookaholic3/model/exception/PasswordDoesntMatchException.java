package com.example.bookaholic3.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class PasswordDoesntMatchException extends RuntimeException {
    public PasswordDoesntMatchException() {
        super("Лозинката не се совпаѓа!");
    }
}
