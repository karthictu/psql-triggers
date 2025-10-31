package com.example.psql_triggers.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.example.psql_triggers.services.SourceTableService;

import reactor.core.publisher.Mono;

public class SourceHandler {

    @Autowired
    private SourceTableService sourceTableService;

    public Mono<ServerResponse> getAllSourceRecords(ServerRequest request) {
        return ServerResponse.ok().bodyValue(sourceTableService.getAllSourceTableDataReactive());
    }

    public Mono<ServerResponse> getSourceRecordById(ServerRequest request) {
        return ServerResponse.ok().bodyValue(sourceTableService.getSourceTableDataByIdReactive(
                request.queryParam("parentId").orElseThrow(), request.queryParam("documentNo").orElseThrow(),
                request.queryParam("equipmentId").orElseThrow()));
    }
}
