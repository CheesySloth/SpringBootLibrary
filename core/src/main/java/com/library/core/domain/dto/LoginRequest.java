package com.library.core.domain.dto;

public record LoginRequest(
        String email,
        String password) {
}
