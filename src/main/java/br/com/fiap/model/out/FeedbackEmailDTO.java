package br.com.fiap.model.out;

import br.com.fiap.model.in.FeedbackDTO;
import br.com.fiap.persistence.entity.Feedback;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackEmailDTO {
    private String aluno;
    private String curso;
    private Short nota;
    private String msg;
    private String email;

    public static FeedbackEmailDTO toFeedbackEmailDTO(Feedback feedbackDTO) {
        FeedbackEmailDTO feedbackEmailDTO = new FeedbackEmailDTO();
        feedbackEmailDTO.setAluno(feedbackDTO.getStudent().getName());
        feedbackEmailDTO.setCurso(feedbackDTO.getCourse().getName());
        feedbackEmailDTO.setMsg(feedbackDTO.getComment());
        feedbackEmailDTO.setNota(feedbackDTO.getGrade());
        feedbackEmailDTO.setEmail("flpnucci@gmail.com");
        return feedbackEmailDTO;
    }
}
