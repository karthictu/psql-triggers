package com.example.psql_triggers.repositories;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.example.psql_triggers.entities.CommonTableId;
import com.example.psql_triggers.entities.SourceUpload;

@Repository
public interface UploadTableReactiveRepository extends ReactiveCrudRepository<SourceUpload, CommonTableId> {
    
}
