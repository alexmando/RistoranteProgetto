package Controller;

import org.example.ristoranteprogetto.Model.Dto.ReservationDTO;
import Service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    /**
     * Gli utenti loggati (USER) possono creare una prenotazione
     */
    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ReservationDTO create(@RequestBody ReservationDTO dto) {
        return reservationService.createReservation(dto);
    }

    /**
     * Gli utenti (USER) visualizzano solo le proprie prenotazioni;
     * gli ADMIN possono visualizzarle tutte
     */
    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<ReservationDTO> getUserReservations(@RequestParam Long userId) {
        return reservationService.getReservationsByUserId(userId);
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
