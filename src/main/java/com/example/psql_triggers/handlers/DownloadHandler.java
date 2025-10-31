package com.example.psql_triggers.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.example.psql_triggers.services.DestinationTableService;

import reactor.core.publisher.Mono;

public class DownloadHandler {
    
    @Autowired private DestinationTableService destinationTableService;

    public Mono<ServerResponse> downloadAllRecords(ServerRequest request) {
        return ServerResponse.ok().bodyValue(destinationTableService.getAllDestinationTableDataReactive());
    }
}
