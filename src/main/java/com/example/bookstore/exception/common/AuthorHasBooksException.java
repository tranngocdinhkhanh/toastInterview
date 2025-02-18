package com.example.bookstore.exception.common;

import com.example.bookstore.exception.CustomException;
import com.example.bookstore.utils.Message;
import lombok.Getter;

@Getter
public class AuthorHasBooksException extends CustomException {
    private final Long authorId;

    public AuthorHasBooksException(Message description, Long authorId) {
        super(description);
        this.authorId = authorId;
    }
}
