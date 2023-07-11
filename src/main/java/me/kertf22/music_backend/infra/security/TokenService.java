package me.kertf22.music_backend.infra.security;


import org.springframework.beans.factory.annotation.Value;

public class TokenService {
    @Value("${api.security.token.secret}")
    private String secret;

    public String validateToken()  {
        return "TokenService.validateToken()";
    }
    public String findByLogin()  {
        return "TokenService.validateToken()";
    }

    public String generateToken()  {
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withIssuer("auth-api")
                    .withSubject(user.getLogin())
                    .withExpiresAt(genExpirationDate())
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Error while generating token", exception);
        }
    }
}
