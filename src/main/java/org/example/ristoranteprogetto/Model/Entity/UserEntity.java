package org.example.ristoranteprogetto.Model.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.LinkedList;

@Data
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String cognome;

    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role ruolo; // ruolo utente o ruolo admin

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private LinkedList<ReservationEntity> prenotazioniUtente;

    public UserEntity(Long id) {
        this.id = id;
    }

    public UserEntity() {
        this.id = null;
    }

    public void setName(String name) {
        this.nome = name;
    }

    public void setRole(Role role) {
        this.ruolo = role;
    }

    public Role getRole() {
        return this.ruolo;
    }
}