package com.library.core.domain.dto;

public record RegisterRequest(
        String name,
        String email,
        String password) {
}
