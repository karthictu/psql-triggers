package com.example.psql_triggers.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.psql_triggers.entities.DestinationTable;
import com.example.psql_triggers.repositories.DestinationTableReactiveRepository;
import com.example.psql_triggers.repositories.DestinationTableRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class DestinationTableService {

    @Autowired
    private DestinationTableRepository destinationTableRepository;
    @Autowired
    private DestinationTableReactiveRepository destinationTableReactiveRepository;

    public List<DestinationTable> getAllDestinationTableData() {
        List<DestinationTable> list = new ArrayList<>();
        destinationTableRepository.findAll().forEach(list::add);
        return list;
    }

    public DestinationTable getDestinationTableDataById(String parentId, String documentNo, String equipmentId) {
        return destinationTableRepository.findByCompositeKey(parentId, documentNo, equipmentId);
    }

    public Flux<DestinationTable> getAllDestinationTableDataReactive() {
        return destinationTableReactiveRepository.findAll();
    }

    public Mono<DestinationTable> getDestinationTableDataByIdReactive(String parentId, String documentNo,
            String equipmentId) {
        return destinationTableReactiveRepository.findByCompositeKey(parentId, documentNo, equipmentId);
    }
}
