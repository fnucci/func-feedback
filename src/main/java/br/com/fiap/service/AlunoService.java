package br.com.fiap.service;

import br.com.fiap.model.in.AlunoDTO;
import br.com.fiap.model.out.AlunoResponse;
import br.com.fiap.persistence.entity.Aluno;
import br.com.fiap.persistence.repository.AlunoRepository;
import br.com.fiap.presenter.AlunoPresenter;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static io.quarkus.hibernate.orm.panache.PanacheEntityBase.listAll;

@ApplicationScoped
public class AlunoService {

    @Inject
    AlunoRepository alunoRepository;

    @Transactional
    public AlunoResponse cadastrarAluno(AlunoDTO alunoDTO) {
        // Lógica para cadastrar um aluno
        Aluno aluno = Aluno.fromAlunoDTO(alunoDTO);
        //cadastra o aluno
        alunoRepository.persist(aluno);
        return AlunoPresenter.toResponse(aluno);
    }

    @Transactional
    public Aluno findById(Long alunoId) {
        // Lógica para cadastrar um aluno
        return alunoRepository.findById(alunoId);
    }

    public List<AlunoResponse> listarAlunos() {
        List<Aluno> alunos = alunoRepository.listAll();
        return alunos.stream()
                .map(AlunoPresenter::toResponse)
                .collect(Collectors.toList());
    }
}
