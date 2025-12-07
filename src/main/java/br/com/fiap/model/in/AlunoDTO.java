package br.com.fiap.model.in;

public record AlunoDTO(
        String name,
        String email,
        String registration,
        Boolean active
) {
}
