package org.example.ristoranteprogetto.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)", updatable = false, nullable = false)
    private UUID id;

    @Version
    @Column(nullable = false)
    private Long version;

    private String username;
    private String password;
    private String email;
    @Enumerated(EnumType.STRING)
    private Role ruolo; // ruolo utente o ruolo admin

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ReservationEntity> prenotazioniUtente = new ArrayList<>();






}