package com.example.SpringProject.dto;

public record PersonRequest(
        String name,
        String surname,
        String birthDate
) {}