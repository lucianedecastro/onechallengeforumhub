package br.com.forumhubalura.forumhubalura.domain.topico.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DadosCadastroTopico(
        @NotBlank(message = "{titulo.obrigatorio}")
        @Size(max = 255, message = "{titulo.tamanhoMaximo}")
        String titulo,

        @NotBlank(message = "{mensagem.obrigatoria}")
        String mensagem,

        @NotBlank(message = "{autor.obrigatorio}")
        String autor,

        @NotBlank(message = "{curso.obrigatorio}")
        String curso
) {
}
