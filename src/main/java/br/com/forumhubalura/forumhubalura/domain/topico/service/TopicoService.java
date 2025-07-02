package br.com.forumhubalura.forumhubalura.domain.topico.service;

import br.com.forumhubalura.forumhubalura.domain.topico.Topico;
import br.com.forumhubalura.forumhubalura.domain.topico.TopicoRepository;
import br.com.forumhubalura.forumhubalura.domain.topico.dto.DadosCadastroTopico;
import br.com.forumhubalura.forumhubalura.domain.topico.dto.DadosAtualizacaoTopico;
import br.com.forumhubalura.forumhubalura.infra.security.exception.ValidacaoException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class TopicoService {

    @Autowired
    private TopicoRepository topicoRepository;

    @Transactional
    public Topico cadastrar(DadosCadastroTopico dados) {

        if (topicoRepository.existsByTituloAndMensagem(dados.titulo(), dados.mensagem())) {
            throw new ValidacaoException("Já existe um tópico com este título e mensagem!");
        }
        var topico = new Topico(dados);
        return topicoRepository.save(topico);
    }

    public Page<Topico> listar(Pageable paginacao) {
        return topicoRepository.findAll(paginacao);
    }

    public Topico detalhar(Long id) {

        return topicoRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public Topico atualizar(Long id, DadosAtualizacaoTopico dados) {

        var topico = topicoRepository.findById(id).orElseThrow(EntityNotFoundException::new);



        topico.atualizarInformacoes(dados);
        return topico;
    }

    @Transactional
    public void excluir(Long id) {

        var topico = topicoRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        topico.inativar();;
    }
}