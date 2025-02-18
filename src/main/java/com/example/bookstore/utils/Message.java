package com.example.bookstore.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Message {
    MSG_103(103, "Not find with id ."),
    MSG_201(201, "Create success."),
    MSG_202(202, "Get object success."),
    MSG_400(400, "Unexpected exception"),
    MSG_410(410, "Object has existed."),
    MSG_411(411,"Cannot delete author because they have books.");
    private final int code;
    private final String description;


}
