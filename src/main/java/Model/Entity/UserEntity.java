package Model.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.net.SocketOption;
import java.util.LinkedList;

@Data
@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    private String nome;
    private String cognome;

    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role ruolo; // ruolo utente o ruolo admin

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private LinkedList<ReservationEntity> prenotazioniUtente;

    public UserEntity(Long id) {
        this.ID = id;
    }

    public UserEntity() {
        this.ID = null;
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

    public void setId(Long userId) {
        this.ID = userId;
    }
}