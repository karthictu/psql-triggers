package com.example.psql_triggers.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.psql_triggers.entities.SourceTable;
import com.example.psql_triggers.repositories.SourceTableReactiveRepository;
import com.example.psql_triggers.repositories.SourceTableRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class SourceTableService {
    @Autowired
    private SourceTableRepository sourceTableRepository;
    @Autowired
    private SourceTableReactiveRepository sourceTableReactiveRepository;

    public List<SourceTable> getAllSourceTableData() {
        List<SourceTable> list = new ArrayList<>();
        sourceTableRepository.findAll().forEach(list::add);
        return list;
    }

    public SourceTable getSourceTableDataById(String parentId, String documentNo, String equipmentId) {
        return sourceTableRepository.findByCompositeKey(parentId, documentNo, equipmentId);
    }

    public Flux<SourceTable> getAllSourceTableDataReactive() {
        return sourceTableReactiveRepository.findAll();
    }

    public Mono<SourceTable> getSourceTableDataByIdReactive(String parentId, String documentNo,
            String equipmentId) {
        return sourceTableReactiveRepository.findByCompositeKey(parentId, documentNo, equipmentId);
    }

}
