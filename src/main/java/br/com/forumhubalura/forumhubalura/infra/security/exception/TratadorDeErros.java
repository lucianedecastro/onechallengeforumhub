package br.com.forumhubalura.forumhubalura.infra.security.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TratadorDeErros {

    // Trata a exceção de entidade não encontrada (por exemplo, quando findById não retorna nada)
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity tratarErro404() {
        return ResponseEntity.notFound().build(); // Retorna 404 Not Found
    }

    // Trata a exceção de argumentos de método inválidos (causada pelas anotações @Valid)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratarErro400(MethodArgumentNotValidException ex) {
        var erros = ex.getFieldErrors(); // Obtém a lista de erros de campo
        // Mapeia cada FieldError para o nosso DTO DadosErroValidacao
        // e coleta em uma lista
        return ResponseEntity.badRequest().body(erros.stream().map(DadosErroValidacao::new).toList());
    }

    // NOVO: Tratamento para o erro 409 CONFLICT (duplicidade)
    // Usaremos uma exceção customizada para isso
    @ExceptionHandler(ValidacaoException.class)
    public ResponseEntity tratarErroRegraDeNegocio(ValidacaoException ex) {
        return ResponseEntity.status(409).body(ex.getMessage());
    }
}