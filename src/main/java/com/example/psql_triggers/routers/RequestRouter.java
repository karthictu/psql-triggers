package com.example.psql_triggers.routers;

import java.util.NoSuchElementException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.example.psql_triggers.handlers.DownloadHandler;
import com.example.psql_triggers.handlers.ErrorHandler;
import com.example.psql_triggers.handlers.SourceHandler;
import com.example.psql_triggers.handlers.UploadHandler;

@Configuration
public class RequestRouter {

    @Bean
    public RouterFunction<ServerResponse> requestRoutes(DownloadHandler downloadHandler, SourceHandler sourceHandler,
            UploadHandler uploadHandler, ErrorHandler errorHandler) {
        return RouterFunctions.nest(RequestPredicates.path("api/reactive"),
                RouterFunctions.route().GET("download", downloadHandler::downloadAllRecords)
                        .GET("source", sourceHandler::getAllSourceRecords)
                        .GET("source/filtered", sourceHandler::getSourceRecordById)
                        .POST("upload", uploadHandler::uploadRecords)
                        .onError(NoSuchElementException.class, errorHandler::handleNoSuchElementException)
                        .onError(Throwable.class, errorHandler::handleOtherErrors).build());
    }
}
