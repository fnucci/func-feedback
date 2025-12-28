package br.com.fiap.exception.utils;

import java.time.LocalDateTime;

public record ApiErrorMessage(
        LocalDateTime timestamp,
        int status,
        String error,
        String cause
) {
}
