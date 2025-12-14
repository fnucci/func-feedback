package br.com.fiap.presenter;

import br.com.fiap.model.out.AlunoResponse;
import br.com.fiap.persistence.entity.Aluno;

public class AlunoPresenter {

    public static AlunoResponse toResponse(Aluno aluno){
        return new AlunoResponse(aluno.getId(), aluno.getName(), aluno.getEmail(), aluno.getCreatedAt());
    }
}
