package br.com.fiap.presenter;

import br.com.fiap.model.out.FeedbackResponse;
import br.com.fiap.model.out.NotaBaixaResponse;

public class NotaBaixaPresenter {

    public static NotaBaixaResponse toResponse(FeedbackResponse feedback) {
        return new NotaBaixaResponse(
                feedback.studentName(),
                feedback.courseName(),
                feedback.grade(),
                "Instrutor, verifique o motivo da baixa nota do aluno e ofereça suporte adequado."
        );
    }
}
