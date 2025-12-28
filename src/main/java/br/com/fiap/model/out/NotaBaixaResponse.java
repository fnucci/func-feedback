package br.com.fiap.model.out;

public record NotaBaixaResponse(
        String aluno,
        String curso,
        Short nota,
        String msg
) {
}
