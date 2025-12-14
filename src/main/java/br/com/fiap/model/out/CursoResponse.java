package br.com.fiap.model.out;

import br.com.fiap.persistence.entity.Aluno;
import br.com.fiap.persistence.entity.ModelType;

import java.time.OffsetDateTime;
import java.util.List;

public record CursoResponse(
        Long id,
        String name,
        ModelType model,
        Boolean active,
        OffsetDateTime createdAt,
        List<Aluno> students
) {
}
