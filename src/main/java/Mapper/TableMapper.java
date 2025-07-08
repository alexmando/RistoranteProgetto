package Mapper;

import Model.Dto.TableDTO;
import Model.Entity.TableEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TableMapper {

    TableDTO toDto(TableEntity table);
    TableEntity toEntity(TableDTO tableDTO);
}
