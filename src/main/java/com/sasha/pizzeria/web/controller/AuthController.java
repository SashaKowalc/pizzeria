package com.sasha.pizzeria.web.controller;

import com.sasha.pizzeria.service.dto.LoginDto;
import com.sasha.pizzeria.web.config.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final JwtUtil jwtUtil;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody LoginDto loginDto) {
        //Creamos un UsernamePasswordAuthenticationToken a partir del usuario y contraseña que nos llego en el request body en el login
        UsernamePasswordAuthenticationToken login = new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());
        //Creamos un objeto de authentication que como valor tiene el llamado al authenticate del authenticationManager
        Authentication authentication = this.authenticationManager.authenticate(login);
        //Internamente este metodo va al authenticationProvider, y este al UserDetailService (UserSecurityService)
        System.out.println(authentication.isAuthenticated()); //Se imprime el valor si se autentico (TRUE or FALSE)
        System.out.println(authentication.getPrincipal()); //Se imprime el usuario como tal en el contexto de seguridad de Spring
        //Authentication Provider valida comparando la authentication que recibimos por el login contra la DB. Si es correcto sigue a las siguientes lineas

        //Se crea un nuevo JWT que le asignamos como respuesta a la solicitud
        String jwt = this.jwtUtil.create(loginDto.getUsername());

        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, jwt).build();
    }

}
