package org.perficient.registrationsystem.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.perficient.registrationsystem.dto.UserDto;
import org.perficient.registrationsystem.model.User;

/**
 * Class UserMaper Created on 20/09/2022
 *
 * @Author Iván Camilo Rincon Saavedra
 */
@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto userToUserDto(User user);

    User userDtoToUser(UserDto userDto);
}
