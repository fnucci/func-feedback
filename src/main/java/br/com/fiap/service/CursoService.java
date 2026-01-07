package br.com.fiap.service;

import br.com.fiap.exception.AlunoJaMatriculadoException;
import br.com.fiap.model.in.CursoDTO;
import br.com.fiap.model.in.MatriculaDTO;
import br.com.fiap.model.out.CursoResponse;
import br.com.fiap.model.out.MatriculaResponse;
import br.com.fiap.persistence.entity.Aluno;
import br.com.fiap.persistence.entity.Curso;
import br.com.fiap.persistence.repository.CursoRepository;
import br.com.fiap.presenter.CursoPresenter;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.stream.Stream;

@ApplicationScoped
public class CursoService {

    @Inject
    CursoRepository cursoRepository;

    @Inject
    AlunoService alunoService;

    @Transactional
    public List<CursoResponse> listarCursos() {
        Stream<Curso> cursoStream = cursoRepository.streamAll();
        return cursoStream
                .map(CursoPresenter::toResponse)
                .collect(java.util.stream.Collectors.toList());
    }

    public Curso findById(Long cursoId) {
        return cursoRepository.findById(cursoId);
    }
}
