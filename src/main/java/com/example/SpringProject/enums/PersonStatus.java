
package com.example.SpringProject.enums;

public enum PersonStatus {
    SUCCESS("success"),
    PENDING("pending"),
    PROCESSING("processing"),
    FAILURE("failure");

    private final String value;

    PersonStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
