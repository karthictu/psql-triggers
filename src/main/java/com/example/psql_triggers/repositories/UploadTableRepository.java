package com.example.psql_triggers.repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.psql_triggers.entities.CommonTableId;
import com.example.psql_triggers.entities.SourceUpload;

public interface UploadTableRepository extends CrudRepository<SourceUpload, CommonTableId> {
    
}
