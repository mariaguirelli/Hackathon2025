package edu.unialfa.java.exception;

public class NotProfessorException extends RuntimeException {
    public NotProfessorException() {
        super("Acesso negado: somente professores podem logar aqui.");
    }
}
