package br.com.forumhubalura.forumhubalura.infra.security;

import br.com.forumhubalura.forumhubalura.infra.security.token.TokenService;
import br.com.forumhubalura.forumhubalura.domain.usuario.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component // Indica que é um componente Spring
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService; // Para validar o token

    @Autowired
    private UsuarioRepository usuarioRepository; // Para buscar o usuário no banco

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var tokenJWT = recuperarToken(request); // Extrai o token do header

        if (tokenJWT != null) {
            var subject = tokenService.getSubject(tokenJWT); // Valida e extrai o login do token
            var usuario = usuarioRepository.findByLogin(subject); // Busca o usuário no banco pelo login

            var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication); // Força a autenticação no Spring Security
        }

        filterChain.doFilter(request, response); // Continua a cadeia de filtros
    }

    private String recuperarToken(HttpServletRequest request) {
        var authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.replace("Bearer ", "");
        }
        return null;
    }
}
