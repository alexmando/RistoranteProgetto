package Controller;

import org.example.ristoranteprogetto.Model.Dto.ReservationDTO;
import org.example.ristoranteprogetto.Model.Dto.TableDTO;
import Service.ReservationService;
import Service.TableService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")   // Tutti i metodi di questo controller sono riservati agli ADMIN
public class AdminController {

    private final TableService tableService;
    private final ReservationService reservationService;

    @GetMapping("/tables")
    public List<TableDTO> getTables() {
        return tableService.getAllTables();
    }

    @PostMapping("/tables")
    public TableDTO createTable(@RequestBody TableDTO dto) {
        return tableService.createTable(dto);
    }

    @PutMapping("/tables/{id}")
    public TableDTO updateTable(@PathVariable Long id, @RequestBody TableDTO dto) {
        return tableService.updateTable(id, dto);
    }

    @DeleteMapping("/tables/{id}")
    public void deleteTable(@PathVariable Long id) {
        tableService.deleteTable(id);
    }

    @GetMapping("/reservations")
    public List<ReservationDTO> allReservations() {
        return reservationService.getAllReservations();
    }

    @PatchMapping("/reservations/{id}/status")
    public ReservationDTO changeStatus(@PathVariable Long id, @RequestParam String status) {
        return reservationService.updateStatus(id, status);
    }
}