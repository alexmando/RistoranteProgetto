package org.example.ristoranteprogetto.Repository;

import Model.Entity.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {

    List<ReservationEntity> findByData(LocalDate data);

    List<ReservationEntity> findByUserId(Long userId);

    List<ReservationEntity> findByTableIdAndDataAndOrario(Long tableId, LocalDate data, LocalTime orario);
}
