package com.dragunov.testapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Client {

    private String clientIp;

    private int requestCount;

    private LocalDateTime availableTimeForRequests;

    private Status status;

}
