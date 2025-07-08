package Mapper;

import Model.Dto.ReservationDTO;
import Model.Entity.ReservationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ReservationMapper {

    ReservationDTO toDto( ReservationEntity  Reservation);
    ReservationEntity toEntity(ReservationDTO ReservationDTO);
}
