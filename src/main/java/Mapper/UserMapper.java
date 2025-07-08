package Mapper;

import Model.Dto.UserDTO;
import Model.Entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring") // così puoi iniettarlo con @Autowired
public interface UserMapper {

    //serve per evitare di mappare campi che non vogliamo mappare, in questo caso la password
    @Mapping(target = "password", ignore = true)
    UserDTO toDto(UserEntity user);
    UserEntity toEntity(UserDTO userDTO);
}