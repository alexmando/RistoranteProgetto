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

    /**
     * Gli utenti loggati (USER) possono creare una prenotazione
     */

    @PostMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<?> create(
            @RequestBody ReservationDTO dto,
            @RequestHeader(name = HttpHeaders.AUTHORIZATION, required = false) String authHeader) {

        // 1) Verifica presenza e formato dell’header Authorization
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Token mancante o non valido");
        }

        // 2) Estrai il token (senza il prefisso "Bearer ")
        String token = authHeader.substring(7);

        // 3) Controlla validità e scadenza del token
        if (!tokenProvider.validateToken(token)) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Token scaduto o non valido");
        }

        // 4) Ottieni l’UUID dell’utente autenticato dal token
        UUID currentUserId;
        try {
            currentUserId = tokenProvider.getUserIdFromToken(token);
        } catch (UsernameNotFoundException ex) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Utente non trovato per il token fornito");
        }

        // 5) Verifica che stia prenotando per sé stesso
        if (!currentUserId.equals(dto.getUserId())) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body("Non puoi creare prenotazioni per altri utenti");
        }

        if (dto.getTableId() == null) {
            dto.setTableId(1L); // valore placeholder temporaneo
        }

        // 6) Creazione della prenotazione
        ReservationDTO created = reservationService.createReservation(dto);
        return ResponseEntity.ok(created);
    }

    /**
     * Gli utenti (USER) visualizzano solo le proprie prenotazioni;
     * gli ADMIN possono visualizzarle tutte
     */
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

    /**
     * Un utente può modificare solo le proprie prenotazioni, l'ADMIN può modificare qualsiasi prenotazione
     */
    /*@PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @reservationSecurity.isOwner(#id, principal.username)")
    public ReservationDTO update(@PathVariable Long id, @RequestBody ReservationDTO dto) {
        return reservationService.updateReservation(id, dto);
    }*/

    /**
     * Un utente può cancellare solo le proprie prenotazioni, l'ADMIN può cancellare qualsiasi prenotazione
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @reservationSecurity.isOwner(#id, principal.username)")
    public void delete(@PathVariable Long id) {
        reservationService.deleteReservation(id);
    }
}
