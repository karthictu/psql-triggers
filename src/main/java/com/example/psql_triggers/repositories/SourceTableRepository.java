package com.example.psql_triggers.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.psql_triggers.entities.SourceTable;
import com.example.psql_triggers.entities.CommonTableId;

@Repository
public interface SourceTableRepository extends CrudRepository<SourceTable, CommonTableId> {
    
    @Query("SELECT s FROM SourceTable s WHERE s.parentId = ?1 AND s.documentNo = ?2 AND s.equipmentId = ?3")
    SourceTable findByCompositeKey(String parentId, String documentNo, String equipmentId);
}
