package com.example.psql_triggers.handlers;

import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.example.psql_triggers.utils.ExceptionUtils;

import reactor.core.publisher.Mono;

public class ErrorHandler {

    public Mono<ServerResponse> handleNoSuchElementException(NoSuchElementException exception, ServerRequest request) {
        return ServerResponse.status(HttpStatus.BAD_REQUEST)
                .bodyValue(ExceptionUtils.buildErrorResponse(exception.getMessage(), request.path()));
    }

    public Mono<ServerResponse> handleOtherErrors(Throwable throwable, ServerRequest request) {
        return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .bodyValue(ExceptionUtils.buildErrorResponse(throwable.getMessage(), request.path()));
    }
}
