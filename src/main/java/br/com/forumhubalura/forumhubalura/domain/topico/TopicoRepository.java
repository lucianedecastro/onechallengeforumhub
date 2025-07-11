package br.com.forumhubalura.forumhubalura.domain.topico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TopicoRepository extends JpaRepository<Topico, Long> {

    boolean existsByTituloAndMensagem(String titulo, String mensagem);
    Page<Topico> findAllByAtivoTrue(Pageable paginacao);
    Optional<Topico> findByIdAndAtivoTrue(Long id);

}
