package br.com.forumhubalura.forumhubalura.domain.topico;

import br.com.forumhubalura.forumhubalura.domain.topico.dto.DadosAtualizacaoTopico;
import br.com.forumhubalura.forumhubalura.domain.topico.dto.DadosCadastroTopico;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "topicos")
@Entity(name = "Topico")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String mensagem;
    private LocalDateTime dataCriacao;
    private Boolean status; 

    private String autor;
    private String curso;

    private Boolean ativo;

    public Topico(DadosCadastroTopico dados) {
        this.titulo = dados.titulo();
        this.mensagem = dados.mensagem();
        this.autor = dados.autor();
        this.curso = dados.curso();
        this.dataCriacao = LocalDateTime.now();
        this.status = true;
        this.ativo = true;
    }

    @PrePersist
    public void prePersist() {
        this.dataCriacao = LocalDateTime.now();
        this.status = true;
        this.ativo = true;
    }

    public void atualizarInformacoes(DadosAtualizacaoTopico dados) {
        if (dados.titulo() != null) {
            this.titulo = dados.titulo();
        }
        if (dados.mensagem() != null) {
            this.mensagem = dados.mensagem();
        }
        if (dados.autor() != null) {
            this.autor = dados.autor();
        }
        if (dados.curso() != null) {
            this.curso = dados.curso();
        }
        if (dados.status() != null) {
            this.status = dados.status();
        }

    }


    public void inativar() {
        this.ativo = false;
    }
}