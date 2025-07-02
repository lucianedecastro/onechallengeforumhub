package br.com.forumhubalura.forumhubalura.infra.security.exception;

public class ValidacaoException extends RuntimeException {
    public ValidacaoException(String message) {
        super(message);
    }
}