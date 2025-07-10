package org.example.ristoranteprogetto.Config;




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




import org.example.ristoranteprogetto.Security.JwtAuthenticationEntryPoint;
import org.example.ristoranteprogetto.Security.JwtAuthenticationFilter;
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

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true) // Abilita @PreAuthorize e @PostAuthorize
public class SecurityConfig {

    private final JwtAuthenticationEntryPoint unauthorizedHandler;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationEntryPoint unauthorizedHandler,
                          JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.unauthorizedHandler = unauthorizedHandler;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(ex -> ex.authenticationEntryPoint(unauthorizedHandler))
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll() // login/registrazione pubblici
                        .anyRequest().authenticated() // il resto richiede autenticazione
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}