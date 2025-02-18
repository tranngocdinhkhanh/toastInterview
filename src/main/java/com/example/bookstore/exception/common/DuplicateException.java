package com.example.bookstore.exception.common;


import com.example.bookstore.exception.CustomException;
import com.example.bookstore.utils.Message;
import lombok.Getter;


@Getter
public class DuplicateException extends CustomException {
    private final String obj;

    public DuplicateException(Message description, String obj) {
        super(description);
        this.obj = obj;
    }
}