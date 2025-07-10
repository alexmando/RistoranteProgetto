package org.example.ristoranteprogetto.Mapper;

import org.example.ristoranteprogetto.Model.Dto.ReservationDTO;
import org.example.ristoranteprogetto.Model.Entity.ReservationEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReservationMapper {

    ReservationDTO toDto( ReservationEntity  Reservation);
    ReservationEntity toEntity(ReservationDTO ReservationDTO);
}
