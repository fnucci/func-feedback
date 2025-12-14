package br.com.fiap.persistence.entity;

import br.com.fiap.model.in.CursoDTO;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Table(name = "cursos")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;


    @ManyToMany
    @JoinTable(
            name = "course_students",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Aluno> students;

    @Column(name = "model", nullable = false, unique = true)
    private ModelType model;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    public static Curso fromCursoDTO(CursoDTO cursoDTO) {
        OffsetDateTime agora = OffsetDateTime.now();
        Curso curso = new Curso();
        curso.setName(cursoDTO.name());
        curso.setModel(cursoDTO.model());
        curso.setActive(cursoDTO.active() != null ? cursoDTO.active() : true);
        curso.setCreatedAt(agora);
        curso.setUpdatedAt(agora);
        return curso;
    }

    public void addStudent(Aluno aluno) {
        this.students.add(aluno);
    }

    public void removeStudent(Aluno aluno) {
        this.students.remove(aluno);
    }
}
