package br.com.forumhubalura.forumhubalura.infra.security.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import br.com.forumhubalura.forumhubalura.domain.usuario.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}") // Injeta a chave secreta do application.properties
    private String secret;

    public String gerarToken(Usuario usuario) {
        try {
            Algorithm algoritmo = Algorithm.HMAC256(secret); // Algoritmo de assinatura com a chave secreta
            return JWT.create()
                    .withIssuer("API ForumHub Alura") // Emissor do token
                    .withSubject(usuario.getLogin()) // Sujeito do token (quem é o usuário)
                    .withExpiresAt(dataExpiracao()) // Data de expiração
                    .sign(algoritmo); // Assina o token
        } catch (JWTCreationException exception){
            throw new RuntimeException("Erro ao gerar token JWT", exception);
        }
    }

    public String getSubject(String tokenJWT) {
        try {
            Algorithm algoritmo = Algorithm.HMAC256(secret);
            return JWT.require(algoritmo)
                    .withIssuer("API ForumHub Alura")
                    .build()
                    .verify(tokenJWT) // Verifica se o token é válido e assinado corretamente
                    .getSubject(); // Retorna o sujeito (login do usuário)
        } catch (JWTVerificationException exception){
            // Token inválido ou expirado
            throw new RuntimeException("Token JWT inválido ou expirado!");
        }
    }

    private Instant dataExpiracao() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00")); // Token expira em 2 horas
    }
}
