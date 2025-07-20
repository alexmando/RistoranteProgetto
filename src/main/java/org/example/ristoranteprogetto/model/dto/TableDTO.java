package org.example.ristoranteprogetto.model.dto;

import jakarta.persistence.*;
import org.example.ristoranteprogetto.model.entity.ReservationEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class TableDTO {

    private Long id;

    private int posti;


    private List<ReservationEntity> prenotazioni;

    public TableDTO() {}

    public TableDTO(Long id, int posti) {
        this.id = id;
        this.posti = posti;
    }

    private Long version;

}
