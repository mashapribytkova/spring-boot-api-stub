package com.example.SpringProject.dto;

public record TokenResponse(
        String id,
        String sub,
        String token
) {}