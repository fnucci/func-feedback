package br.com.fiap.model.in;

public record FeedbackDTO(
        Long alunoId,
        Long cursoId,
        String comentario,
        Short grade
) {
}
