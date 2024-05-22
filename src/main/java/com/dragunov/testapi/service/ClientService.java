package com.dragunov.testapi.service;

import com.dragunov.testapi.model.Client;
import com.dragunov.testapi.model.Status;
import com.dragunov.testapi.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    @Value("${time-second.unit}")
    private int timeUnitSecond;

    public Client getOrSaveClient(String ip) {
        if (clientRepository.get(ip).isEmpty()) {
            clientRepository.save(new Client(ip, 0, LocalDateTime.now().plusSeconds(timeUnitSecond), Status.READY));
            log.info("Client ip - {} saved", ip);
        }
        return clientRepository.get(ip).get();
    }

    public void updateClient(Client oldClient, Client newClient) {
        clientRepository.update(oldClient, newClient);
    }
}
