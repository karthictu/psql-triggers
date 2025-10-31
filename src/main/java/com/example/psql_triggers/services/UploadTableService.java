package com.example.psql_triggers.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.psql_triggers.entities.SourceUpload;
import com.example.psql_triggers.repositories.UploadTableReactiveRepository;
import com.example.psql_triggers.repositories.UploadTableRepository;

import reactor.core.publisher.Mono;

@Service
public class UploadTableService {
    
    @Autowired private UploadTableRepository uploadTableRepository;

    @Autowired private UploadTableReactiveRepository uploadTableReactiveRepository;

    public List<SourceUpload> saveAll(List<SourceUpload> uploads) {
        List<SourceUpload> insertedList = List.of();
        uploadTableRepository.saveAll(uploads).forEach(insertedList::add);
        return insertedList;
    }

    public Mono<SourceUpload> saveReactive(SourceUpload record) {
        return uploadTableReactiveRepository.save(record);
    }
}
