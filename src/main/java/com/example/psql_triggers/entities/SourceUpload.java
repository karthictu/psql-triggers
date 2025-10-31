package com.example.psql_triggers.entities;

import org.springframework.data.relational.core.mapping.Table;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "source_table", schema = "myschema")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@IdClass(CommonTableId.class)
public class SourceUpload {
    @Id
    private String parentId;
    @Id
    private String documentNo;
    @Id
    private String equipmentId;
    @Column
    private String partyCode;
    @Column
    private String currentStatus;
    @Column
    private String receiverCityCode;
    @Column
    private String receiverCityName;
    @Column
    private String userId;
    @Column
    private String comments;
    @Column
    private String totalAmount;
    @Column
    private String currencyCode;
    @Column
    private String category;
    @Column
    private String totalAmountInUsd;
}
