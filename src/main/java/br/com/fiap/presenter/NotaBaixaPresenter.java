package br.com.fiap.presenter;

import br.com.fiap.model.out.NotaBaixaResponse;
import br.com.fiap.persistence.entity.Feedback;

public class NotaBaixaPresenter {

    public static NotaBaixaResponse toResponse(Feedback feedback) {
        return new NotaBaixaResponse(
                feedback.getStudent().getName(),
                feedback.getCourse().getName(),
                feedback.getGrade(),
                "Instrutor, verifique o motivo da baixa nota do aluno e ofereça suporte adequado.",
                "flpnucci@gmail.com"
        );
    }
}
