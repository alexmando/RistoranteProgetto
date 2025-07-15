package org.example.ristoranteprogetto.repository;

import org.example.ristoranteprogetto.model.entity.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {

    List<ReservationEntity> findByData(LocalDate data);

    List<ReservationEntity> findByUserId(UUID userId);

    List<ReservationEntity> findByTableIdAndDataAndOrario(Long tableId, LocalDate data, LocalTime orario);
}
