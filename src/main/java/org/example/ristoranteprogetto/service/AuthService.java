package org.example.ristoranteprogetto.service;

import lombok.RequiredArgsConstructor;
import org.example.ristoranteprogetto.security.CustomUserDetailsService;
import org.example.ristoranteprogetto.security.JwtTokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public String login(String email, String rawPassword) {
        // 1) Carico manualmente i details
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        String storedHash = userDetails.getPassword();

        // 2) Verifico il match e stampo in console
        boolean ok = passwordEncoder.matches(rawPassword, storedHash);
        System.out.println("LOGIN DEBUG: rawPassword=" + rawPassword);
        System.out.println("LOGIN DEBUG: storedHash  =" + storedHash);
        System.out.println("LOGIN DEBUG: matches?   =" + ok);

        if (!ok) {
            // Qui vedi esattamente se è false
            throw new BadCredentialsException("Password non corrisponde all'hash");
        }

        // 3) Solo se il match è vero, procedi
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, rawPassword)
        );

        return jwtTokenProvider.generateToken(authentication);
    }
}
