package com.example.psql_triggers.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "destination_table", schema = "myschema")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@IdClass(CommonTableId.class)
public class DestinationTable {
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
    private String refreshTime;
    @Column
    private String receiverCountryCode;
    @Column
    private String receiverCountryName;
    @Column
    private String receiverRegionCode;
    @Column
    private String receiverCityCode;
    @Column
    private String receiverCityName;
    @Column
    private String senderCountryCode;
    @Column
    private String senderCountryName;
    @Column
    private String senderRegionCode;
    @Column
    private String senderCityCode;
    @Column
    private String senderCityName;
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
