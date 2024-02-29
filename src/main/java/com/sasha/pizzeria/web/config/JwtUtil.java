package com.sasha.pizzeria.web.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component //para inyectar la clase en el ciclo de vida de Spring
public class JwtUtil {

  private static String SECRET_KEY = "s@sh@Kow@lc";

  private static Algorithm ALGORITHM = Algorithm.HMAC256(SECRET_KEY);

  public String create(String username) {
    return JWT.create()
        .withSubject(username) //indica el asunto, que en este caso siempre sera el usuario en cuestion
        .withIssuer("sashaKowalc") //indica quien crea este JWT
        .withIssuedAt(new Date()) //indica la fecha en la cual se crea este JWT
        .withExpiresAt(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(15))) //indica cuando expira este token (15 dias)
        .sign(ALGORITHM);
  }

  //METODO QUE PERMITE VALIDAR SI UN JWT ES CORRECTO O NO
  public Boolean isValid(String jwt) {
    try {
      JWT.require(ALGORITHM) //esta instancia requiere el algoritmo con el cual estamos encriptando nuestro jwt
          .build() //una vez que lo requerimos, lo construimos
          .verify(jwt); //recibe el string que es el token

      return true;
      //Si el jwt no es valido, se lanzara una exception de tipo JWTVerificationException
    } catch (JWTVerificationException e) {
      return false;
    }
  }

  //METODO PARA SABER A QUE USUARIO/SUBJECT PERTENECE EL TOKEN
  public String getUsername(String jwt) {
    return JWT.require(ALGORITHM)
        .build()
        .verify(jwt)
        .getSubject();
  }

}


