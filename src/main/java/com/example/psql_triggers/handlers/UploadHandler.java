package com.example.psql_triggers.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.example.psql_triggers.entities.SourceUpload;
import com.example.psql_triggers.services.UploadTableService;

import reactor.core.publisher.Mono;

public class UploadHandler {

    @Autowired
    private UploadTableService uploadTableService;

    public Mono<ServerResponse> uploadRecords(ServerRequest request) {
        return ServerResponse.ok().bodyValue(
                request.bodyToFlux(SourceUpload.class).doOnNext(data -> uploadTableService.saveReactive(data)));
    }
}
