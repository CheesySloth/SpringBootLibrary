package com.library.core.auth;

public record AuthRequest(
        String email,
        String password) {

}
