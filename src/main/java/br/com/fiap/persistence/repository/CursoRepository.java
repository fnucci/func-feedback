package br.com.fiap.persistence.repository;

import br.com.fiap.persistence.entity.Curso;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CursoRepository implements PanacheRepository<Curso> {
}
