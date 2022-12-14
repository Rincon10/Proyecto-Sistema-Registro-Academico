package org.perficient.registrationsystem.services.impl;

import org.perficient.registrationsystem.dto.UserDto;
import org.perficient.registrationsystem.mappers.UserMapper;
import org.perficient.registrationsystem.model.User;
import org.perficient.registrationsystem.repositories.UserRepository;
import org.perficient.registrationsystem.services.UserService;
import org.perficient.registrationsystem.services.exceptions.ServerErrorException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

/**
 * Class UserServiceImpl Created on 18/10/2022
 *
 * @Author Iván Camilo Rincon Saavedra
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    private UserDto updateUser(User user, UserDto userDto) throws ServerErrorException {
        try {
            user.update(userDto);
            user.setPassword(userDto.getPassword());
            return userMapper
                    .userToUserDto(userRepository.save(user));
        } catch (Exception e) {
            throw new ServerErrorException("We couldn't Updated the user " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    private User findUserByEmail(String email) throws ServerErrorException {
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ServerErrorException("The User that you are looking for doesn't exists.", HttpStatus.NOT_FOUND.value()));

        return user;
    }

    private User findUserById(int id) throws ServerErrorException {
        var user = userRepository.findById((long) id)
                .orElseThrow(() -> new ServerErrorException("The User that you are looking for doesn't exists.", HttpStatus.NOT_FOUND.value()));

        return user;
    }

    @Override
    public Set<UserDto> getAllUsers(int pageNo, int pageSize) throws Exception {
        Set<UserDto> set = new HashSet<>();
        Pageable pageable = PageRequest.of(pageNo, pageSize);

        userRepository.findAll(pageable)
                .iterator()
                .forEachRemaining(u -> {
                    u.setPassword(null);
                    set.add(userMapper.userToUserDto(u));
                });
        return set;
    }

    @Override
    public UserDto getUserById(int id) throws Exception {
        var userDto = userMapper
                .userToUserDto(findUserById(id));

        return userDto;
    }

    @Override
    public UserDto getUserByEmail(String email) throws Exception {
        var userDto = userMapper
                .userToUserDto(findUserByEmail(email));

        return userDto;
    }

    @Override
    public UserDto addUser(UserDto userDto) throws Exception {
        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new ServerErrorException("User already exits!", HttpStatus.FORBIDDEN.value());
        }
        try {
            var user = userMapper.userDtoToUser(userDto);
            user.setPassword(userDto.getPassword());

            return userMapper.userToUserDto(userRepository.save(user));
        } catch (DataIntegrityViolationException e) {
            throw new ServerErrorException("User already exits!", HttpStatus.FORBIDDEN.value());
        } catch (Exception e) {
            throw new ServerErrorException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @Transactional
    @Override
    public UserDto updateUserById(int id, UserDto userDto) throws Exception {
        var user = findUserById(id);
        return updateUser(user, userDto);
    }

    @Transactional
    @Override
    public UserDto updateUserByEmail(String email, UserDto userDto) throws Exception {
        var user = findUserByEmail(email);
        return updateUser(user, userDto);
    }

    @Transactional
    @Override
    public Boolean deleteByEmail(String email) throws Exception {
        // we verify that the user is already created
        findUserByEmail(email);
        userRepository.deleteByEmail(email);

        return true;
    }
}
