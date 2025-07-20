package org.example.ristoranteprogetto.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.antlr.v4.runtime.misc.NotNull;
import org.example.ristoranteprogetto.model.entity.Role;
import lombok.Data;

import java.util.UUID;

@Data
public class UserDTO {

    private String username;
    @NotNull
    private String password;
    @NotNull
    private String email;

    private Role ruolo;// "ROLE_USER" o "ROLE_ADMIN"

    private UUID id;

    public UserDTO() {}

    public UserDTO( String email, Role ruolo) {
        this.email = email;
        this.ruolo = ruolo;
    }

    public UserDTO(String email, Object o) {
        this.email = email;

    }


    private Long version;



    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public String getPassword() {
        return password;
    }

    public Role getRuolo() {
        return this.ruolo;
    }
}