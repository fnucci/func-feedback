package br.com.fiap.service;

import br.com.fiap.persistence.entity.Aluno;
import br.com.fiap.persistence.repository.AlunoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class AlunoService {

    @Inject
    AlunoRepository alunoRepository;

    @Transactional
    public Aluno findById(Long alunoId) {
        // Lógica para cadastrar um aluno
        return alunoRepository.findById(alunoId);
    }
}
