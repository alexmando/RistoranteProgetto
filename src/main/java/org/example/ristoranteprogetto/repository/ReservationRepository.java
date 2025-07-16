package org.example.ristoranteprogetto.repository;

import org.example.ristoranteprogetto.model.entity.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {

    List<ReservationEntity> findByDataPrenotazione(LocalDateTime dataPrenotazione);

    List<ReservationEntity> findByUserId(UUID userId);

    Optional<ReservationEntity> findByTableIdAndDataPrenotazione(Long id, LocalDateTime dataPrenotazione);
}
