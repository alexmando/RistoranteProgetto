package org.example.ristoranteprogetto.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Setter
@Getter
public class ReservationDTO {
    private Long id;
    private LocalDate data;
    private LocalTime orario;
    private int numeroPersone;
    private String status;  // es. "PENDING", "APPROVED", "REJECTED"

    private Long version;

    public ReservationDTO() {}

    public ReservationDTO(Long id, LocalDate data, LocalTime orario, int persone, String status) {
        this.id = id;
        this.data = data;
        this.orario = orario;
        this.numeroPersone = persone;
        this.status = status;
    }

    public LocalDate getDate() {
        return data;
    }

    public void setDate(LocalDate date) {
        this.data = date;
    }

    public LocalTime getTime() {
        return orario;
    }

    public void setTime(LocalTime time) {
        this.orario = time;
    }

    public int getPeople() {
        return numeroPersone;
    }

    public void setPeople(int people) {
        this.numeroPersone = people;
    }


}