package com.example.psql_triggers.repositories;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.example.psql_triggers.entities.SourceTable;

import reactor.core.publisher.Mono;

import com.example.psql_triggers.entities.CommonTableId;

@Repository
public interface SourceTableReactiveRepository extends ReactiveCrudRepository<SourceTable, CommonTableId> {

    @Query("SELECT s FROM SourceTable s WHERE s.parentId = :parentId AND s.documentNo = :documentNo AND s.equipmentId = :equipmentId")
    Mono<SourceTable> findByCompositeKey(String parentId, String documentNo, String equipmentId);
}