package com.dragunov.testapi.controller;

import com.dragunov.testapi.dto.DocumentDto;
import com.dragunov.testapi.model.Status;
import com.dragunov.testapi.model.Client;
import com.dragunov.testapi.service.ClientService;
import com.dragunov.testapi.service.QuotaService;
import com.dragunov.testapi.service.WebService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ApiController {

    private final WebService webService;

    private final ClientService clientService;

    private final QuotaService quotaService;

    @PostMapping("/v3/lk/documents/create")
    public ResponseEntity<String> createDocument(HttpServletRequest req, @RequestBody DocumentDto document, @RequestHeader String signature) {
        String ip = webService.getClientIp(req);
        Client client = clientService.getOrSaveClient(ip);
        if (client.getStatus().equals(Status.CLOSED)) {
            quotaService.checkStatus(client);
            return ResponseEntity.badRequest().body("Request quota is end");
        }
        quotaService.checkStatus(client);
        quotaService.addRequestCount(client);
        return ResponseEntity.ok("Document: " + document + "\n" + "Signature: " + signature);
    }
}
