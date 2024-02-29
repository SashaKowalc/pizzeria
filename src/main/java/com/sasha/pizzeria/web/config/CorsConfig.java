package com.sasha.pizzeria.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
public class CorsConfig {

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration corsConfiguration = new CorsConfiguration();

    corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:4200")); //Permite la lista de servidores que lo van a consumir
    corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE")); //Permite la lista de metodos http que se pueden solicitar desde otro servidor
    corsConfiguration.setAllowedHeaders(Arrays.asList("*")); //Permite la lista de headers que se pueden enviar con la solicitud

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", corsConfiguration);
    return source;
  }

}
