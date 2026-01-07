package br.com.fiap.model.out;

import java.time.OffsetDateTime;

public record AlunoResponse(
        Long id,
        String name,
        String email,
        OffsetDateTime createdAt
) {
}
