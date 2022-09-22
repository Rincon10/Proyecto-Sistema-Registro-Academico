package org.perficient.registrationsystem.services.impl;

import org.perficient.registrationsystem.dto.UserDto;
import org.perficient.registrationsystem.mappers.UserMapper;
import org.perficient.registrationsystem.model.User;
import org.perficient.registrationsystem.repositories.UserRepository;
import org.perficient.registrationsystem.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * Class UserServiceImpl Created on 22/09/2022
 *
 * @Author Iván Camilo Rincon Saavedra
 */
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final UserMapper userMapper;

    public UserServiceImpl(@Autowired UserRepository userRepository, @Autowired UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }


    @Override
    public Set<User> getUsers() {
        Set<User> users = new HashSet<>();

        userRepository.findAll().iterator().forEachRemaining(users::add);
        return users;
    }

    @Override
    public User addUser(UserDto userDto) {
        User user = userMapper.userDtoToUser( userDto);

        return userRepository.save(user);
    }
}
