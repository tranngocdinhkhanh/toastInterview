package com.example.bookstore.exception.common;



import com.example.bookstore.exception.CustomException;
import com.example.bookstore.utils.Message;
import lombok.Getter;

@Getter
public class NotFoundException extends CustomException {
    private final Long id;

    public NotFoundException(Message description, Long id) {
        super(description);
        this.id = id;
    }
}
