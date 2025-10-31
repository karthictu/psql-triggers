package com.example.psql_triggers.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.psql_triggers.entities.SourceTable;
import com.example.psql_triggers.services.SourceTableService;

@RestController
@RequestMapping("api/source")
public class SourceTableController {
    @Autowired
    private SourceTableService sourceTableService;

    @GetMapping
    public ResponseEntity<List<SourceTable>> getAllSourceRecords(@RequestHeader(required = true) String authHeader) {
        List<SourceTable> data = sourceTableService.getAllSourceTableData();
        return data.size() > 0 ? ResponseEntity.ok(data) : ResponseEntity.noContent().build();
    }

    @GetMapping(path = "filtered")
    public ResponseEntity<SourceTable> getById(@RequestHeader(required = true) String authHeader,
            @RequestParam(required = true) String parentId, @RequestParam(required = true) String documentNo,
            @RequestParam(required = true) String equipmentId) {
        SourceTable data = sourceTableService.getSourceTableDataById(parentId, documentNo, equipmentId);
        return data != null ? ResponseEntity.ok(data) : ResponseEntity.notFound().build();
    }

}
