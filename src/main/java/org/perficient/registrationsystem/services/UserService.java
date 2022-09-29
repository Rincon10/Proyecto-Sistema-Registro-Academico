package org.perficient.registrationsystem.services;

import org.perficient.registrationsystem.dto.UserDto;

import java.util.Set;

/**
 * Interface UserService Created on 22/09/2022
 *
 * @Author Iván Camilo Rincon Saavedra
 */
public interface UserService {
    Set<UserDto> getUsers();

    UserDto addUser(UserDto userDto);

}
