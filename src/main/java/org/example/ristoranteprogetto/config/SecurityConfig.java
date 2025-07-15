package org.example.ristoranteprogetto.config;




//In particolare, nel contesto di SecurityConfig:
//Definisce come Spring Security deve comportarsi nel tuo progetto.
//
//Imposta le regole di accesso alle risorse (quali URL sono pubblici, quali richiedono autenticazione, ecc.).
//
//Configura i filtri di sicurezza (ad esempio, un filtro JWT che intercetta ogni richiesta per validare il token).
//
//Imposta il gestore di errori per risposte non autorizzate (come JwtAuthenticationEntryPoint).
//
//Configura la gestione della sessione, nel tuo caso per dire a Spring di non mantenere sessioni perché usi JWT (stateless).
//
//Definisce il PasswordEncoder per criptare le password.
//
//Inietta il AuthenticationManager che si occupa di verificare le credenziali.




import lombok.RequiredArgsConstructor;
import org.example.ristoranteprogetto.security.CustomUserDetailsService;
import org.example.ristoranteprogetto.security.JwtAuthenticationEntryPoint;
import org.example.ristoranteprogetto.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor          // Lombok: costruttore con tutti i final
public class SecurityConfig {

    private final JwtAuthenticationEntryPoint unauthorizedHandler;
    private final JwtAuthenticationFilter     jwtAuthenticationFilter;
    private final CustomUserDetailsService    customUserDetailsService;

    /* 🔑 Encoder ‑‑ obbligatorio se le password vengono confrontate in database */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



    /* 🌍 CORS – solo localhost:4200 in sviluppo */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration c = new CorsConfiguration();
        c.setAllowedOrigins(List.of("http://localhost:4200"));
        c.setAllowedMethods(List.of("*"));
        c.setAllowedHeaders(List.of("*"));
        c.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource s = new UrlBasedCorsConfigurationSource();
        s.registerCorsConfiguration("/**", c);
        return s;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration
    ) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(c -> c.configurationSource(corsConfigurationSource()))
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(e -> e.authenticationEntryPoint(unauthorizedHandler))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/", "/index.html", "/health",
                                "/auth/**", "/static/**", "/*.ico", "/error")
                        .permitAll()
                        .anyRequest().authenticated())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
