package org.example.ristoranteprogetto.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

// ogni prenotazione è legata ad un utente e ad uno specifico tavolo,
// mentre un tavolo e un utente possono avere molteplici prenotazioni.

@Data
@Entity
@jakarta.persistence.Table(name = "prenotazioni")
public class ReservationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private LocalDateTime dataPrenotazione;

    private int numeroPersone;

    @Getter
    @Setter
    private String status; // es. "IN_ATTESA", "CONFERMATA", "RIFIUTATA"

    @Version
    @Column(nullable = false)
    private Long version = 0L;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "table_id", nullable = true) //tavolo assegnabile in seguito, fatto per non avere problemi nel json
    private TableEntity table;


    public void setPeople(int people) {
        this.numeroPersone = people;
    }

    public int getPeople() {
        return this.numeroPersone;
    }

}