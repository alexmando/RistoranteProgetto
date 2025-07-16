package org.example.ristoranteprogetto.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Setter
@Getter
public class ReservationDTO {

    private Long id;
    private LocalDateTime dataPrenotazione;
    private int numeroPersone;
    private String status;  // es. "PENDING", "APPROVED", "REJECTED"

    private Long version;

    private UUID userId;
    private Long tableId;

    public ReservationDTO() {}

    public ReservationDTO(Long id, LocalDateTime orario, int numeroPersone, String status, UUID userId, Long tableId, Long version) {
        this.id=id;
        this.dataPrenotazione = orario;
        this.numeroPersone = numeroPersone;
        this.status = status;
        this.userId = userId;
        this.tableId = tableId;
        this.version = version;
    }

}