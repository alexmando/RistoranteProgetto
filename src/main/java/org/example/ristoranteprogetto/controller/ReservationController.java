package org.example.ristoranteprogetto.controller;

import org.example.ristoranteprogetto.model.dto.ReservationDTO;
import org.example.ristoranteprogetto.security.JwtTokenProvider;
import org.example.ristoranteprogetto.security.UserDetailsImpl;
import org.example.ristoranteprogetto.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/prenotazioni")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;
    private final JwtTokenProvider tokenProvider;


    @PostMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<?> create( @RequestBody ReservationDTO dto, @RequestHeader(name = HttpHeaders.AUTHORIZATION, required = false) String authHeader) {

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Token mancante o non valido");
        }
        String token = authHeader.substring(7);

        if (!tokenProvider.validateToken(token)) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Token scaduto o non valido");
        }
        UUID currentUserId;
        try {
            currentUserId = tokenProvider.getUserIdFromToken(token);
        } catch (UsernameNotFoundException ex) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Utente non trovato per il token fornito");
        }
        if (!currentUserId.equals(dto.getUserId())) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body("Non puoi creare prenotazioni per altri utenti");
        }

        if (dto.getTableId() == null) {
            dto.setTableId(1L);
        }
        ReservationDTO created = reservationService.createReservation(dto);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<?> getUserReservations(@PathVariable UUID userId,
                                                 @RequestHeader("Authorization") String authHeader) {

        String token = authHeader.substring(7);
        UUID currentUserId = tokenProvider.getUserIdFromToken(token);
        boolean isAdmin = tokenProvider.getRolesFromToken(token).contains("ADMIN");

        // Un USER può vedere solo le proprie prenotazioni
        if (!isAdmin && !currentUserId.equals(userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Accesso negato: puoi vedere solo le tue prenotazioni");
        }

        return ResponseEntity.ok(reservationService.getReservationsByUserId(userId));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @reservationSecurity.isOwner(#id, principal.username)")
    public void delete(@PathVariable Long id) {
        reservationService.deleteReservation(id);
    }


    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<ReservationDTO>  getReservations() {
        return reservationService.getAllReservations();
    }
}
