package org.example.ristoranteprogetto.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

//classe end point che serve per gestire situazione di non autorizzato ad entrare
import jakarta.servlet.ServletException;


@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint { //Entry point sicurezza: Primo
    // componente chiamato quando un utente non autenticato cerca di accedere a risorse protette
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Accesso non autorizzato");
    }
}
