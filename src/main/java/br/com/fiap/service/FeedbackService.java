package br.com.fiap.service;

import br.com.fiap.exception.AlunoNaoMatriculadoException;
import br.com.fiap.model.in.FeedbackDTO;
import br.com.fiap.model.out.FeedbackResponse;
import br.com.fiap.persistence.entity.Aluno;
import br.com.fiap.persistence.entity.Curso;
import br.com.fiap.persistence.entity.Feedback;
import br.com.fiap.persistence.repository.FeedbackRepository;
import io.quarkus.arc.Unremovable;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Unremovable
@ApplicationScoped
@Slf4j
public class FeedbackService {

    @Inject
    private AlunoService alunoService;

    @Inject
    private CursoService cursoService;

    @Inject
    private FeedbackRepository feedbackRepository;

    @Transactional
    public FeedbackResponse cadastrarFeedback(FeedbackDTO feedbackDTO) {

        Aluno aluno = alunoService.findById(feedbackDTO.getAlunoId());

        Curso curso = cursoService.findById(feedbackDTO.getCursoId());

        log.info("Feedback DTO recebido: {}", feedbackDTO);


        if (curso.getStudents().contains(aluno)) {
            Feedback feedback = Feedback.fromFeedbackDTO(feedbackDTO, aluno, curso);
            feedbackRepository.persist(feedback);

            return new FeedbackResponse(feedback.getStudent().getName(), feedback.getCourse().getName(), feedback.getGrade(), feedback.getComment());
        } else {
            throw new AlunoNaoMatriculadoException("Apenas alunos matriculados podem enviar feedbacks.");
        }
    }
}
