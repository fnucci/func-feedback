package br.com.fiap.persistence.entity;

import br.com.fiap.model.in.FeedbackDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Entity
@Table(name = "feedbacks")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "student_id", nullable = false)
    private Aluno student;

    @ManyToOne(optional = false)
    @JoinColumn(name = "course_id", nullable = false)
    private Curso course;

    @Column(name = "grade", nullable = false)
    private Short grade;

    @Column(name = "comment")
    private String comment;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    public static Feedback fromFeedbackDTO(FeedbackDTO feedbackDTO, Aluno aluno, Curso curso) {
        OffsetDateTime agora = OffsetDateTime.now();
        Feedback feedback = new Feedback();
        feedback.setStudent(aluno);
        feedback.setCourse(curso);
        feedback.setGrade(feedbackDTO.getGrade());
        feedback.setComment(feedbackDTO.getComentario());
        feedback.setCreatedAt(agora);
        feedback.setUpdatedAt(agora);
        return feedback;
    }
}
