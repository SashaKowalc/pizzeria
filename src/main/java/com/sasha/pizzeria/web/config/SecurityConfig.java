package com.sasha.pizzeria.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration //@Configuration permite autogestionar esta clase para que tenga toda la configuracion de seguridad de la aplicación.
@EnableMethodSecurity(securedEnabled = true) //Con esta anotacion y este parametro le permitimos a Spring controlar el tipo de anotaciones como
//@Secured que colocamos en el service para determinar que roles pueden acceder a tal metodo (OrderService en nuestro caso).
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    @Autowired
    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean //@Bean permite autogestionar este metodo en spring
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .authorizeHttpRequests(customizeRequests -> {
              customizeRequests                                                          //Con ** permite aplicar el parametro a todos despues del /
                  //.requestMatchers(HttpMethod.GET,"/api/pizzas/*").permitAll() //Con * solo permitimos el primer nivel despues del /
                  .requestMatchers("/api/auth/**").permitAll()
                  .requestMatchers("/api/customers/**").hasAnyRole("ADMIN", "CUSTOMER")
                  .requestMatchers(HttpMethod.GET,"/api/pizzas/**").hasAnyRole("ADMIN", "CUSTOMER")
                  .requestMatchers(HttpMethod.POST,"/api/pizzas/**").hasRole("ADMIN")
                  .requestMatchers(HttpMethod.PUT).hasRole("ADMIN")
                  .requestMatchers("/api/orders/random").hasAuthority("random_order")
                  .requestMatchers("/api/orders/**").hasRole("ADMIN")
                  .anyRequest() //para cualquier peticion
                  .authenticated(); //necesitamos estar autenticados
            }
        )
        .csrf(AbstractHttpConfigurer::disable)
        .cors(Customizer.withDefaults()) //politicas cors para acceder desde otros servidores
        .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
      return configuration.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

}
