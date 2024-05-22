package com.dragunov.testapi.repository;

import com.dragunov.testapi.model.Client;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class ClientRepository {
    private final List<Client> clients = new CopyOnWriteArrayList<>();

    public void save(Client client) {
        clients.add(client);
    }

    public Optional<Client> get(String clientIp) {
        Client empty = new Client();
        empty.setClientIp("0");
        return clients.stream()
                .filter(client -> client.getClientIp().equals(clientIp)).findFirst();
    }

    public void update(Client oldClient, Client newClient) {
        clients.remove(oldClient);
        clients.add(newClient);
    }
}
