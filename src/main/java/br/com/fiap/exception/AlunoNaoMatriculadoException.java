package br.com.fiap.exception;

public class AlunoNaoMatriculadoException extends RuntimeException {
    public AlunoNaoMatriculadoException(String message) {
        super(message);
    }
}
