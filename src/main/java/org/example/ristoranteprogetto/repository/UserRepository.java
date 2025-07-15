package org.example.ristoranteprogetto.repository;

import org.example.ristoranteprogetto.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    Optional<UserEntity> findByEmail(String email);

    //gli altri metodi classici CRUD base come find by id, update ecc sono forniti in automatico dall'interfaccia.
    //unico metodo differente è trovare l'utente tramite la mail che glielo abbiamo scritto sopra, il sistema
    //implementerà automaticamente il metodo capendo dal nome del metodo cosa ci serve realmente.
}
