package com.example.psql_triggers.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.psql_triggers.entities.DestinationTable;
import com.example.psql_triggers.services.DestinationTableService;

@RestController
@RequestMapping(path = "api/download")
public class DownloadController {
    @Autowired
    private DestinationTableService destinationTableService;

    @GetMapping
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public List<DestinationTable> getAllRecords(@RequestHeader(required = true) String authHeader) {
        return destinationTableService.getAllDestinationTableData();
    }
}
