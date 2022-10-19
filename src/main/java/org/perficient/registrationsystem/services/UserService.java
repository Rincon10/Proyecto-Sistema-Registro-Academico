package org.perficient.registrationsystem.services;

import org.perficient.registrationsystem.dto.UserDto;

import java.util.Set;

/**
 * Interface UserService Created on 22/09/2022
 *
 * @Author Iván Camilo Rincon Saavedra
 */
public interface UserService {

    //GET
    Set<UserDto> getAllUsers() throws Exception;

    UserDto getUserById(int id) throws Exception;

    UserDto getUserByEmail(String email) throws Exception;

    //POST
    UserDto addUser(UserDto userDto) throws Exception;

    //UPDATE
    UserDto updateUserById(int id, UserDto userDto) throws Exception;

    UserDto updateUserByEmail(String email, UserDto userDto) throws Exception;

    //DELETE
    Boolean deleteByEmail(String email) throws Exception;
}
