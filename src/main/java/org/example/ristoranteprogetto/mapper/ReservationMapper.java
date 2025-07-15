package org.example.ristoranteprogetto.mapper;

import org.example.ristoranteprogetto.model.dto.ReservationDTO;
import org.example.ristoranteprogetto.model.entity.ReservationEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReservationMapper {

    ReservationDTO toDto( ReservationEntity  Reservation);
    ReservationEntity toEntity(ReservationDTO ReservationDTO);
}
