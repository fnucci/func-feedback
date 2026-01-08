package br.com.fiap.model.out;

public record FeedbackResponse (
        String studentName,
        String courseName,
        Short grade,
        String mensagem
) {

}
