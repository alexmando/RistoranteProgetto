package org.example.ristoranteprogetto.controller;

import jakarta.transaction.Transactional;
import org.example.ristoranteprogetto.model.dto.UserDTO;
import org.example.ristoranteprogetto.service.AuthService;
import org.example.ristoranteprogetto.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthService authService;        // ⬅️  niente più AuthenticationManager

    /* ---------- REGISTER ---------- */
    @PostMapping("/register")
    @Transactional
    public ResponseEntity<?> register(@RequestBody UserDTO userDTO) {
        UserDTO registered = userService.createUser(userDTO);
        return ResponseEntity.ok(registered);
    }

    /* ---------- LOGIN ---------- */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDTO userDTO) {
        try {
            System.out.println("LOGIN DTO: email=" + userDTO.getEmail() + " password=" + userDTO.getPassword());
            String token = authService.login(userDTO.getEmail(), userDTO.getPassword());
            return ResponseEntity.ok(Map.of("token", "Bearer " + token));

        } catch (AuthenticationException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Credenziali non valide");
        }
    }
}