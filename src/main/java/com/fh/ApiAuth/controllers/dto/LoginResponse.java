package com.fh.ApiAuth.controllers.dto;

public record LoginResponse(String accessToken, Long expiresIn) {
}
