package org.carly.core.shared.config;

public enum ErrorCode {

    NOT_FOUND("Entity not found.");

    private String description;

    ErrorCode(String description) {
        this.description = description;
    }
}