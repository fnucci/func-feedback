package br.com.fiap.exception;

public class CursoNotFoundException extends RuntimeException {
    public CursoNotFoundException(String message) {
        super(message);
    }
}
