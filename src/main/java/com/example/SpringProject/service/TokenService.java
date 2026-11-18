
package com.example.SpringProject.service;

import com.example.SpringProject.dto.PersonRequest;
import com.example.SpringProject.dto.TokenResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Base64;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.nio.charset.StandardCharsets;

import com.example.SpringProject.enums.PersonStatus;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class TokenService {

    private final List<PersonRequest> persons =
            new CopyOnWriteArrayList<>();

    private final Set<String> tokens =
            ConcurrentHashMap.newKeySet();

    public TokenResponse getToken(String sub) {
        randomDelay();

        String id = UUID.randomUUID().toString();

        String token = Base64.getEncoder()
                .encodeToString(
                        id.getBytes(StandardCharsets.UTF_8)
                );

        tokens.add(token);

        return new TokenResponse(id, sub, token);
    }


    public PersonStatus createPerson(
            PersonRequest person,
            String authorization
    ) {
        randomDelay();

        validateToken(authorization);

        PersonStatus[] statuses = PersonStatus.values();

        int randomIndex = ThreadLocalRandom.current()
                .nextInt(statuses.length);

        PersonStatus status = statuses[randomIndex];

        if (status == PersonStatus.SUCCESS) {
            persons.add(person);
        }

        return status;
    }


    public PersonRequest getPerson(String authorization) {
        randomDelay();

        validateToken(authorization);

        if (persons.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "List of persons is empty"
            );
        }

        return persons.get(0);
    }

    private void validateToken(String authorization) {
        if (authorization == null ||
                !authorization.startsWith("Bearer ")) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED
            );
        }

        String token = authorization.substring(7);

        if (!tokens.contains(token)) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED
            );
        }
    }


    private void randomDelay() {
        int delay = ThreadLocalRandom.current().nextInt(100, 301);

        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Request interrupted", e);
        }
    }

}
