package com.example.psql_triggers.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.psql_triggers.entities.CommonTableId;
import com.example.psql_triggers.entities.DestinationTable;

@Repository
public interface DestinationTableRepository extends CrudRepository<DestinationTable, CommonTableId> {

    @Query("SELECT d FROM DestinationTable d WHERE d.parentId = ?1 AND d.documentNo = ?2 AND d.equipmentId = ?3")
    DestinationTable findByCompositeKey(String parentId, String documentNo, String equipmentId);
    
}
