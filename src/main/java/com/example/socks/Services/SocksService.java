package com.example.socks.Services;

import com.example.socks.Model.Color;
import com.example.socks.Model.Size;
import com.example.socks.Model.Socks;
import com.example.socks.dto.SockRequest;
import com.example.socks.exception.InSufficientSockQuantityException;
import com.example.socks.exception.InvalidSockRequestException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

@Service
public class SocksService {
    private final ObjectMapper objectMapper;
    private final Map<Socks, Integer> socks = new HashMap<>();

    public SocksService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public void addSock(SockRequest sockRequest) {
        validateRequest(sockRequest);
        Socks sock = mapToSock(sockRequest);
        if (socks.containsKey(sock)) {
            socks.put(sock, socks.get(sock) + sockRequest.getQuantity());
        } else {
            socks.put(sock, sockRequest.getQuantity());
        }
    }

    public FileSystemResource exportData() throws IOException {
        Path filePath = Files.createTempFile("export-", ".json");
        Files.write(filePath,
                objectMapper.writeValueAsBytes(this.socks));
        return new FileSystemResource(filePath);
    }
    public void issueSock(SockRequest sockRequest) {
        decreaseSockQuantity(sockRequest);
    }
    public void removeDefectedSocks(SockRequest sockRequest) {
        decreaseSockQuantity(sockRequest);
    }

    private void decreaseSockQuantity(SockRequest sockRequest) {
        validateRequest(sockRequest);
        Socks sock = mapToSock(sockRequest);
        int sockQuantity = socks.getOrDefault(sock, 0);
        if (sockQuantity >= sockRequest.getQuantity()) {
            socks.put(sock, sockQuantity - sockRequest.getQuantity());
        } else {
            throw new InSufficientSockQuantityException("There is no socks");
        }
    }

    public int getSockQuantity(Color color, Size size, Integer cottonMin, Integer cottonMax) {
        int total = 0;
        for (Map.Entry<Socks, Integer> entry : socks.entrySet()) {
            if (color != null && !entry.getKey().getColor().equals(color)) {
                continue;
            }
            if (size != null && !entry.getKey().getSize().equals(size)) {
                continue;
            }
            if (cottonMin != null && entry.getKey().getCottonPercent() < cottonMin) {
                continue;
            }
            if (cottonMax != null && entry.getKey().getCottonPercent() > cottonMax) {
                continue;
            }
            total += entry.getValue();
        }
        return total;
    }

    private void validateRequest(SockRequest sockRequest) {
        if (sockRequest.getColor() == null || sockRequest.getSize() == null) {
            throw new InvalidSockRequestException("All fields should be filled");
        }
        if (sockRequest.getQuantity() <= 0) {
            throw new InvalidSockRequestException("Quantity must to be more than 0");
        }
        if (sockRequest.getCottonPercent() < 0 || sockRequest.getCottonPercent() > 100) {
            throw new InvalidSockRequestException("Cotton percent should to be between 0 and 100%");
        }
    }

    private Socks mapToSock(SockRequest sockRequest) {
        return new Socks(sockRequest.getColor(),
                sockRequest.getSize(),
                sockRequest.getCottonPercent());
    }



}



