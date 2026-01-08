package br.com.fiap.service;

import br.com.fiap.exception.AlunoNaoMatriculadoException;
import br.com.fiap.model.in.FeedbackDTO;
import br.com.fiap.model.out.FeedbackResponse;
import br.com.fiap.persistence.entity.Aluno;
import br.com.fiap.persistence.entity.Curso;
import br.com.fiap.persistence.entity.Feedback;
import br.com.fiap.persistence.repository.FeedbackRepository;
import br.com.fiap.presenter.NotaBaixaPresenter;
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

    @Inject
    private QueueService queueService;

    @Transactional
    public FeedbackResponse cadastrarFeedback(FeedbackDTO feedbackDTO) {

        Aluno aluno = alunoService.findById(feedbackDTO.getAlunoId());

        Curso curso = cursoService.findById(feedbackDTO.getCursoId());

        log.info("Feedback DTO recebido: {}", feedbackDTO);


        if (curso.getStudents().contains(aluno)) {
            Feedback feedback = Feedback.fromFeedbackDTO(feedbackDTO, aluno, curso);
            feedbackRepository.persist(feedback);

            if (feedback.getGrade() < 5) {
                log.info("Nota menor que 5 detectada. Enviando mensagem para a fila SQS.");
                queueService.sendMessage(NotaBaixaPresenter.toResponse(feedback).toString());
            }
            return new FeedbackResponse("Feedback registrado com sucesso");

        } else {
            throw new AlunoNaoMatriculadoException("Apenas alunos matriculados podem enviar feedbacks.");
        }
    }
}
