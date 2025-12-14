package br.com.fiap.presenter;

import br.com.fiap.model.out.CursoResponse;
import br.com.fiap.persistence.entity.Curso;

import java.util.stream.Collectors;

public class CursoPresenter {

    public static CursoResponse toResponse(Curso curso) {
        return new CursoResponse(curso.getId(), curso.getName(), curso.getModel(), curso.isActive(), curso.getCreatedAt(),
                curso.getStudents()
                        .stream()
                        .map(AlunoPresenter::toResponse)
                        .collect(Collectors.toList()));
    }
}
