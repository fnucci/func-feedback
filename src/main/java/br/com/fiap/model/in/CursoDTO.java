package br.com.fiap.model.in;

import br.com.fiap.persistence.entity.ModelType;

public record CursoDTO(
        String name,
        ModelType model,
        Boolean active
) {
}
