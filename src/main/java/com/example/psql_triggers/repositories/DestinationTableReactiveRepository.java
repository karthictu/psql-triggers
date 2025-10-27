package com.example.psql_triggers.repositories;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.example.psql_triggers.entities.CommonTableId;
import com.example.psql_triggers.entities.DestinationTable;

import reactor.core.publisher.Mono;

@Repository
public interface DestinationTableReactiveRepository extends ReactiveCrudRepository<DestinationTable, CommonTableId> {

    @Query("SELECT d FROM DestinationTable d WHERE d.parentId = :parentId AND d.documentNo = :documentNo AND d.equipmentId = :equipmentId")
    Mono<DestinationTable> findByCompositeKey(String parentId, String documentNo, String equipmentId);
    
}
