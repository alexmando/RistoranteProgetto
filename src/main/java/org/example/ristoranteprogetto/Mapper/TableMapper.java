package org.example.ristoranteprogetto.Mapper;

import org.example.ristoranteprogetto.Model.Dto.TableDTO;
import org.example.ristoranteprogetto.Model.Entity.TableEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TableMapper {

    TableDTO toDto(TableEntity table);
    TableEntity toEntity(TableDTO tableDTO);
}
