package edu.unialfa.java.exception;

public class PrecisaAlterarSenhaException extends RuntimeException {
    public PrecisaAlterarSenhaException() {
        super("Altere a senha padrão no painel web antes de logar.");
    }
}
