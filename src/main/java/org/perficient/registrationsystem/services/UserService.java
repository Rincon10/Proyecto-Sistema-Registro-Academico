package org.perficient.registrationsystem.services;

import org.perficient.registrationsystem.dto.UserDto;
import org.perficient.registrationsystem.model.User;

import java.util.Set;

/**
 * Interface UserService Created on 22/09/2022
 *
 * @Author Iván Camilo Rincon Saavedra
 */
public interface UserService {
    Set<User> getUsers();

    User addUser(UserDto userDto);

}
