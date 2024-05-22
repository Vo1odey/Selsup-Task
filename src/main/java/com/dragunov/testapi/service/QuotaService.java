package com.dragunov.testapi.service;

import com.dragunov.testapi.model.Client;
import com.dragunov.testapi.model.Status;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
@Slf4j
@Service
@RequiredArgsConstructor
public class QuotaService {

    @Value("${request.limit}")
    private int requestQuotaSecond;

    @Value("${time-second.unit}")
    private int timeUnitSecond;

    public void checkQuota(Client client) {
        if (client.getAvailableTimeForRequests().isBefore(LocalDateTime.now())) {
            log.info("quota for ip: {} granted", client.getClientIp());
            client.setStatus(Status.READY);
            client.setAvailableTimeForRequests(LocalDateTime.now().plusSeconds(timeUnitSecond));
            client.setRequestCount(0);
            log.info("available requests for ip: {} - {}", client.getClientIp(), requestQuotaSecond);
        }
        if (client.getRequestCount() == requestQuotaSecond) {
            client.setStatus(Status.CLOSED);
        }
    }

    public void addRequestCount(Client client) {
        client.setRequestCount(client.getRequestCount() + 1);
        checkQuota(client);
    }
}
