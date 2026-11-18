
package com.example.SpringProject.controller;

import com.example.SpringProject.dto.PersonRequest;
import com.example.SpringProject.dto.TokenResponse;
import com.example.SpringProject.service.TokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.SpringProject.enums.PersonStatus;

import java.util.Map;

@RestController
public class TokenController {

    private final TokenService tokenService;

    public TokenController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @GetMapping("/api/access/token")
    public TokenResponse getToken(
            @RequestHeader("sub") String sub
    ) {
        return tokenService.getToken(sub);
    }

    @PostMapping("/api/create/person")
    public ResponseEntity<?> createPerson(
            @RequestBody PersonRequest person,
            @RequestHeader(
                    value = "Authorization",
                    required = false
            ) String authorization
    ) {
        PersonStatus status =
                tokenService.createPerson(person, authorization);

        return ResponseEntity.ok(
                Map.of("status", status.getValue())
        );
    }


    @GetMapping("/api/person")
    public ResponseEntity<?> getPerson(
            @RequestHeader(
                    value = "Authorization",
                    required = false
            ) String authorization
    ) {
        return ResponseEntity.ok(
                tokenService.getPerson(authorization)
        );
    }
}
