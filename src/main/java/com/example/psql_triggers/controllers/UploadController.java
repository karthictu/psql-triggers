package com.example.psql_triggers.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.psql_triggers.entities.SourceUpload;
import com.example.psql_triggers.models.UploadResponse;
import com.example.psql_triggers.services.UploadTableService;

@RestController
@RequestMapping(path = "api/download")
public class UploadController {

    @Autowired
    private UploadTableService uploadTableService;

    @PostMapping
    public ResponseEntity<UploadResponse> uploadRecords(@RequestHeader(required = true) String authHeader,
            @RequestBody(required = true) List<SourceUpload> uploads) {
        return ResponseEntity.status(201).body(UploadResponse.builder().message("Upload successful")
                .count(uploadTableService.saveAll(uploads).size()).build());
    }
}
