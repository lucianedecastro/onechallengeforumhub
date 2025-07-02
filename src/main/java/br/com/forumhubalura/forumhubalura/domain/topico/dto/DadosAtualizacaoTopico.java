package br.com.forumhubalura.forumhubalura.domain.topico.dto;

import jakarta.validation.constraints.Size;

public record DadosAtualizacaoTopico(
        @Size(max = 255, message = "{titulo.tamanhoMaximo}")
        String titulo,
        String mensagem,
        String autor,
        String curso,
        Boolean status
) {
}
