package com.example.socks.Controllers;

import com.example.socks.Model.Color;
import com.example.socks.Model.Size;
import com.example.socks.Services.SocksService;
import com.example.socks.dto.SockRequest;
import com.example.socks.exception.InSufficientSockQuantityException;
import com.example.socks.exception.InvalidSockRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/socks")
public class SockController {
    private final SocksService socksService;

    public SockController(SocksService socksService) {
        this.socksService = socksService;
    }


    @ExceptionHandler(InvalidSockRequestException.class)
    public ResponseEntity<String> handleInvalidException(InvalidSockRequestException invalidSockRequestException) {
        return ResponseEntity.badRequest().body(invalidSockRequestException.getMessage());
    }
    @ExceptionHandler(InSufficientSockQuantityException.class)
    public ResponseEntity<String> handleInvalidException(InSufficientSockQuantityException inSufficientSockQuantityException) {
        return ResponseEntity.badRequest().body(inSufficientSockQuantityException.getMessage());
    }


    @PostMapping
    public void addSocks(@RequestBody SockRequest sockRequest) {
        socksService.addSock(sockRequest);
    }

    @PutMapping
    public void issueSock(@RequestBody SockRequest sockRequest) {
        socksService.issueSock(sockRequest);
    }

    @GetMapping
    public int getSocksCount(@RequestParam(required = false, name = "color") Color color,
                             @RequestParam(required = false, name = "size") Size size,
                             @RequestParam(required = false, name = "cottonMin") Integer cottonMin,
                             @RequestParam(required = false, name = "cottonMax") Integer cottonMax) {
        return socksService.getSockQuantity(color, size, cottonMin, cottonMax);

    }

    @DeleteMapping
    public void removeDefectedSocks(@RequestBody SockRequest sockRequest) {
        socksService.removeDefectedSocks(sockRequest);
    }


}