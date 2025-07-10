package org.example.ristoranteprogetto.Model.Dto;

import org.example.ristoranteprogetto.Model.Entity.Role;
import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String nome;
    private String cognome;
    private String email;
    private String password;
    private Role ruolo;  // "ROLE_USER" o "ROLE_ADMIN"

    public UserDTO() {}

    public UserDTO(Long id, String nome, String email, Role ruolo) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.ruolo = ruolo;
    }

    public UserDTO(String email, Object o) {
        this.email = email;

    }

    // Non è consigliato tenere la password in DTO, ma metto qui un getter vuoto
    public String getPassword() {
        return null;
    }


    public String getName() {
        return nome;
    }

    public Role getRole() {
        return this.ruolo;
    }
}