package com.example.bookstore.exception.handler;


import com.example.bookstore.dto.response.Response;
import com.example.bookstore.exception.CustomException;
import com.example.bookstore.exception.common.AuthorHasBooksException;
import com.example.bookstore.exception.common.DuplicateException;
import com.example.bookstore.exception.common.NotFoundException;
import com.example.bookstore.utils.Message;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Response<Long>> handleNotFoundException(NotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Response.<Long>builder()
                        .code(ex.getDescription().getCode())
                        .description(ex.getDescription().getDescription())
                        .data(ex.getId())
                        .build());
    }

    @ExceptionHandler(DuplicateException.class)
    public ResponseEntity<Response<String>> handleDuplicateException(DuplicateException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Response.<String>builder()
                        .code(ex.getDescription().getCode())
                        .description(ex.getDescription().getDescription())
                        .data(ex.getObj())
                        .build());
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Response<Void>> handleCustomException(CustomException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Response.<Void>builder()
                        .code(ex.getDescription().getCode())
                        .description(ex.getDescription().getDescription())
                        .build());
    }

    @ExceptionHandler(AuthorHasBooksException.class)
    public ResponseEntity<Response<Long>> handleAuthorHasBooksException(AuthorHasBooksException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Response.<Long>builder()
                        .code(ex.getDescription().getCode())
                        .description(ex.getDescription().getDescription())
                        .data(ex.getAuthorId())
                        .build());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Response<String>> handleConstraintViolationException(ConstraintViolationException ex) {
        List<String> errorMessages = ex.getConstraintViolations().stream()
                .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                .toList();

        return ResponseEntity.badRequest().body(new Response<>(Message.MSG_400.getCode(), String.join(", ", errorMessages), null));
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response<Exception>> handleGenericException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Response.<Exception>builder()
                        .code(Message.MSG_400.getCode())
                        .description(Message.MSG_400.getDescription())
                        .data(ex)
                        .build());
    }
}
