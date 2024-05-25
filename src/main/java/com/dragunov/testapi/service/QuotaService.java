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
    private int requestQuota;

    @Value("${time-second.unit}")
    private int timeUnitSecond;

    private boolean isAvailableRequestTime(Client client) {
        return client.getAvailableTimeForRequests().isBefore(LocalDateTime.now());
    }

    public void checkStatus(Client client) {
        if (isAvailableRequestTime(client)) {
            log.info("request for ip {} is open", client.getClientIp());
            client.setStatus(Status.READY);
            client.setAvailableTimeForRequests(LocalDateTime.now().plusSeconds(timeUnitSecond));
            client.setRequestCount(0);
        }
    }

    private boolean isClosed(Client client) {
        return client.getRequestCount() == requestQuota;
    }

    public void addRequestCount(Client client) {
        client.setRequestCount(client.getRequestCount() + 1);
        log.info("client request count: {}", client.getRequestCount());
        if (isClosed(client)) {
            log.info("request for ip {} is closed", client.getClientIp());
            client.setStatus(Status.CLOSED);
        }
    }
}
