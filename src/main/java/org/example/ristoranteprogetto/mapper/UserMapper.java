package org.example.ristoranteprogetto.mapper;

import org.example.ristoranteprogetto.model.dto.UserDTO;
import org.example.ristoranteprogetto.model.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring") // così puoi iniettarlo con @Autowired
public interface UserMapper {


    //serve per evitare di mappare campi che non vogliamo mappare, in questo caso la password
    @Mapping(target = "password", ignore = true)
    UserDTO toDto(UserEntity user);

    @Mapping(target = "version", ignore = true)
    UserEntity toEntity(UserDTO userDTO);
}