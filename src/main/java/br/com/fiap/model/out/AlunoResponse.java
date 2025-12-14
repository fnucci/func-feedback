package br.com.fiap.model.out;

import br.com.fiap.persistence.entity.Curso;

import java.time.OffsetDateTime;
import java.util.List;

public record AlunoResponse (
        Long id,
        String name,
        String email,
        OffsetDateTime createdAt
) {
}
