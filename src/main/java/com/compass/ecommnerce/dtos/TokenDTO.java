package com.compass.ecommnerce.dtos;


public record TokenDTO(java.time.Instant issuedAt, String token, java.time.Instant expiresAt) {
}
