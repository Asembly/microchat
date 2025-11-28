package asembly.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.sql.Timestamp;
import java.util.Date;

public class Jwt {
    public static String genJwt(String username, String secret, Long exp){
        try{
            Algorithm alg = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withSubject(username)
                    .withIssuer("auth0")
                    .withIssuedAt(new Date())
                    .withExpiresAt(new Date(new Date().getTime() + exp))
                    .sign(alg);
        }catch (Exception e) {
            throw new RuntimeException("Failed to generate JWT token", e);
        }
    }

    public static boolean verifyJwt(String token, String secret)
    {
        try{
            Algorithm alg = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(alg)
                    .withIssuer("auth0")
                    .build();
            verifier.verify(token);
            return true;
        }catch (JWTVerificationException e) {
            return false;
        }
    }

    public static Timestamp getIssuedAt(String token, String secret)
    {
        try {
            Algorithm alg = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(alg)
                    .withIssuer("auth0")
                    .build();
            DecodedJWT decodedJWT = verifier.verify(token);
            return Timestamp.from(decodedJWT.getIssuedAt().toInstant());
        }catch(JWTVerificationException exception)
        {
            return null;
        }
    }

    public static Timestamp getExpiresAt(String token, String secret)
    {
        try {
            Algorithm alg = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(alg)
                    .withIssuer("auth0")
                    .build();
            DecodedJWT decodedJWT = verifier.verify(token);
            return Timestamp.from(decodedJWT.getExpiresAt().toInstant());
        }catch(JWTVerificationException exception)
        {
            return null;
        }
    }

    public static String getUsernameFromJwt(String token, String secret)
    {
        DecodedJWT decodedJWT;
        try{
            Algorithm alg = Algorithm.HMAC256(secret);
            decodedJWT = JWT.require(alg)
                    .build()
                    .verify(token);
            return decodedJWT.getSubject();
        }catch (JWTVerificationException e) {
            throw new JWTVerificationException(e.getMessage() + "\nInvalid signature/claims", e.getCause());
        }
    }

}
