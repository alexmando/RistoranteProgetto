package org.example.ristoranteprogetto.controller;

import org.example.ristoranteprogetto.model.dto.ReservationDTO;
import org.example.ristoranteprogetto.security.JwtTokenProvider;
import org.example.ristoranteprogetto.security.UserDetailsImpl;
import org.example.ristoranteprogetto.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<?> create(@RequestBody ReservationDTO dto, @RequestHeader("Authorization") String authHeader) {

        // Estrai e valida il token
        String token = authHeader.substring(7);
        if (!tokenProvider.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token non valido o scaduto");
        }

        // Ottieni l'ID utente dal token
        Long userId = tokenProvider.getUserIdFromToken(token);

        // Verifica che l'utente stia creando una prenotazione per sé stesso
        if (!userId.equals(dto.getUserId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Non puoi creare prenotazioni per altri utenti");
        }

        ReservationDTO createdReservation = reservationService.createReservation(dto);
        return ResponseEntity.ok(createdReservation);
    }

    /**
     * Gli utenti (USER) visualizzano solo le proprie prenotazioni;
     * gli ADMIN possono visualizzarle tutte
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<?> getUserReservations(@RequestParam UUID userId,
                                                 @RequestHeader("Authorization") String authHeader) {

        String token = authHeader.substring(7);
        Long currentUserId = tokenProvider.getUserIdFromToken(token);
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
