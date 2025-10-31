package com.example.psql_triggers.utils;

import java.time.LocalDateTime;

import com.example.psql_triggers.models.ErrorResponse;

public class ExceptionUtils {

    public static ErrorResponse buildErrorResponse(String message, String uri) {
        return ErrorResponse.builder().message(message).timestamp(LocalDateTime.now()).uri(uri).build();
    }
}
