package org.example.ristoranteprogetto.mapper;

import org.example.ristoranteprogetto.model.dto.TableDTO;
import org.example.ristoranteprogetto.model.entity.TableEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TableMapper {

    TableDTO toDto(TableEntity table);
    TableEntity toEntity(TableDTO tableDTO);
}
