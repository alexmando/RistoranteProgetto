package org.example.ristoranteprogetto.controller;

import org.example.ristoranteprogetto.model.dto.ReservationDTO;
import org.example.ristoranteprogetto.model.dto.TableDTO;
import org.example.ristoranteprogetto.model.dto.UserDTO;
import org.example.ristoranteprogetto.service.ReservationService;
import org.example.ristoranteprogetto.service.TableService;
import lombok.RequiredArgsConstructor;
import org.example.ristoranteprogetto.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")   // Tutti i metodi di questo controller sono riservati agli ADMIN
@CrossOrigin(origins = "http://localhost:4200")
public class AdminController {

    private final TableService tableService;
    private final ReservationService reservationService;
    private final UserService userService;

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

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    //solo admin può cancellare un utente
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
    }

}