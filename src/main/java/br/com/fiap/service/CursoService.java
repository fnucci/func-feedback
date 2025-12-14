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
    public CursoResponse cadastrarCurso(CursoDTO cursoDTO) {
        // Lógica para cadastrar um aluno
        Curso curso = Curso.fromCursoDTO(cursoDTO);
        //cadastra o aluno
        cursoRepository.persist(curso);
        return CursoPresenter.toResponse(curso);
    }

    @Transactional
    public MatriculaResponse matricularAluno(MatriculaDTO matriculaDTO) {
        // Lógica para cadastrar um aluno
        Curso curso = cursoRepository.findById(matriculaDTO.cursoId());

        Aluno aluno = alunoService.findById(matriculaDTO.alunoId());

        if(curso.getStudents().contains(aluno)) {
            throw new AlunoJaMatriculadoException("Aluno já está matriculado nesse curso.");
        }else
            curso.addStudent(aluno);

        //cadastra o aluno
        cursoRepository.persist(curso);
        return new MatriculaResponse("Aluno matriculado com sucesso !");
    }

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
