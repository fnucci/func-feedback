package br.com.fiap.persistence.entity;

import br.com.fiap.model.in.AlunoDTO;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Table(name = "alunos")

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Aluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "registration", nullable = false, unique = true)
    private String registration;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    @ManyToMany(mappedBy = "students")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Curso> courses;


    public static Aluno fromAlunoDTO(AlunoDTO alunoDTO) {
        OffsetDateTime agora = OffsetDateTime.now();
        Aluno aluno = new Aluno();
        aluno.setName(alunoDTO.name());
        aluno.setEmail(alunoDTO.email());
        aluno.setRegistration(alunoDTO.registration());
        aluno.setCreatedAt(agora);
        aluno.setUpdatedAt(agora);
        return aluno;
    }
}
