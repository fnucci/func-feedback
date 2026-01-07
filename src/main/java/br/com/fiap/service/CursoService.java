package br.com.fiap.service;

import br.com.fiap.persistence.entity.Curso;
import br.com.fiap.persistence.repository.CursoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class CursoService {

    @Inject
    CursoRepository cursoRepository;

    @Transactional
    public Curso findById(Long cursoId) {
        return cursoRepository.findById(cursoId);
    }
}
