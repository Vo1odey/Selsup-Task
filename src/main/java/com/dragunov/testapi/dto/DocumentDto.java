package com.dragunov.testapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class DocumentDto {
    private Description description;
    @JsonProperty("doc_id")
    private String docId;

    @JsonProperty("doc_status")
    private String docStatus;

    @JsonProperty("doc_type")
    private String docType;

    private boolean importRequest;

    @JsonProperty("owner_inn")
    private String ownerInn;

    @JsonProperty("participant_inn")
    private String participantInn;

    @JsonProperty("producer_inn")
    private String producerInn;

    @JsonProperty("production_date")
    private LocalDate productionDate;

    @JsonProperty("production_type")
    private String productionType;

    private List<Product> products;

    @JsonProperty("reg_date")
    private LocalDate regDate;

    @JsonProperty("reg_number")
    private String regNumber;
}
