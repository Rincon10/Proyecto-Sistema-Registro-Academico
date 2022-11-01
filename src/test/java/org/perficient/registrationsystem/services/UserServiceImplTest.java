package org.perficient.registrationsystem.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.perficient.registrationsystem.dto.UserDto;
import org.perficient.registrationsystem.mappers.UserMapper;
import org.perficient.registrationsystem.model.User;
import org.perficient.registrationsystem.repositories.UserRepository;
import org.perficient.registrationsystem.services.exceptions.ServerErrorException;
import org.perficient.registrationsystem.services.impl.UserServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.crypto.bcrypt.BCrypt;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    private final UserMapper userMapper = UserMapper.INSTANCE;

    @Mock
    private UserRepository userRepository;
    private UserServiceImpl service;

    private User userTest;
    private UserDto userDtoTest;

    @Before
    public void setUp() throws Exception {
        userTest = User.builder()
                .firstName("Ivan Camilo")
                .lastName("Rincon Saavedra")
                .email("ivan.rincon-s@mail.escuelaing.edu.co")
                .build();
        userTest.setPassword("123456");
        userDtoTest = userMapper.userToUserDto(userTest);

        service = new UserServiceImpl(userRepository, userMapper);
    }

    @Test
    public void getAllUsers() throws Exception {
        // Arrange
        int expectedSize = 1;

        Page<User> userList = new PageImpl<>(List.of(userTest));

        when(userRepository.findAll(any(Pageable.class)))
                .thenReturn(userList);

        // Act
        Set<UserDto> set = service.getAllUsers(0,5);

        // Assert
        assertThat(set).hasSize(expectedSize);
        assertThat(set.toArray()[0])
                .usingRecursiveComparison()
                .isEqualTo(userList.getContent().get(0));

        verify(userRepository, times(expectedSize))
                .findAll(any(Pageable.class));
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void getUserById() throws Exception {
        // Arrange
        when(userRepository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(userTest));

        // Act
        var userSaved = service.getUserById(anyInt());

        // Assert
        assertThat(userSaved)
                .usingRecursiveComparison()
                .isEqualTo(userDtoTest);
        verify(userRepository, times(1))
                .findById(anyLong());
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void shouldFailByInvalidId() {
        // Arrange
        when(userRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        // Act
        Assertions.assertThrows(ServerErrorException.class, () -> service.getUserById(anyInt()));

        // Assert
        verify(userRepository, times(1))
                .findById(anyLong());
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void getUserByEmail() throws Exception {
        // Arrange
        when(userRepository.findByEmail(anyString()))
                .thenReturn(Optional.ofNullable(userTest));

        // Act
        var userSaved = service.getUserByEmail(anyString());

        // Assert
        assertThat(userSaved)
                .usingRecursiveComparison()
                .isEqualTo(userDtoTest);
        verify(userRepository, times(1))
                .findByEmail(anyString());

        verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void shouldFailByInvalidEmail() {
        // Arrange
        when(userRepository.findByEmail(anyString()))
                .thenReturn(Optional.empty());

        // Act
        Assertions.assertThrows(ServerErrorException.class, () -> service.getUserByEmail(anyString()));

        // Assert
        verify(userRepository, times(1))
                .findByEmail(anyString());
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void addUserIfTheUserDoesntExits() throws Exception {
        // Arrange
        when(userRepository.findByEmail(anyString()))
                .thenReturn(Optional.empty());

        when(userRepository.save(any(User.class)))
                .thenReturn(userTest);

        // Act
        var savedUserDto = service.addUser(userDtoTest);

        // Assert
        assertThat(savedUserDto)
                .isEqualTo(userMapper.userToUserDto(userTest));

        verify(userRepository, times(1))
                .save(any(User.class));
        verify(userRepository, times(1))
                .findByEmail(anyString());
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void shouldFailAddingAUserIfAlreadyExists() {
        // Arrange
        when(userRepository.findByEmail(anyString()))
                .thenReturn(Optional.ofNullable(userTest));

        // Act && Assert
        Assertions.assertThrows(ServerErrorException.class, () -> service.addUser(userDtoTest));
    }

    @Test
    public void updateUserById() throws Exception {
        String oldPassword = "123456";
        String newPassword = "1234567";

        // Arrange
        userDtoTest.setPassword(newPassword);

        when(userRepository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(userTest));

        when(userRepository.save(any(User.class)))
                .thenReturn(userTest);

        // Act
        var userUpdated = service.updateUserById(anyInt(), userDtoTest);

        // Assert
        Assertions.assertTrue(BCrypt.checkpw(newPassword, userUpdated.getPassword()));
        Assertions.assertFalse(BCrypt.checkpw(oldPassword, userUpdated.getPassword()));
    }

    @Test
    public void shouldFailUpdatingUserById() {
        // Arrange
        when(userRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        // Act && Assert

        Assertions.assertThrows(ServerErrorException.class, () -> service.updateUserById(anyInt(), userDtoTest));
        verify(userRepository, times(1))
                .findById(anyLong());
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void updateUserByEmail() throws Exception {
        String oldPassword = "123456";
        String newPassword = "1234567";

        // Arrange
        userDtoTest.setPassword(newPassword);

        when(userRepository.findByEmail(anyString()))
                .thenReturn(Optional.ofNullable(userTest));

        when(userRepository.save(any(User.class)))
                .thenReturn(userTest);

        // Act
        var userUpdated = service.updateUserByEmail(anyString(), userDtoTest);

        // Assert
        Assertions.assertTrue(BCrypt.checkpw(newPassword, userUpdated.getPassword()));
        Assertions.assertFalse(BCrypt.checkpw(oldPassword, userUpdated.getPassword()));
    }

    @Test
    public void shouldFailUpdatingAUserByEmailByIncorrectEmail() {
        // Arrange
        when(userRepository.findByEmail(anyString()))
                .thenReturn(Optional.empty());

        // Act && Assert

        Assertions.assertThrows(ServerErrorException.class, () -> service.updateUserByEmail(anyString(), userDtoTest));
        verify(userRepository, times(1))
                .findByEmail(anyString());
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void deleteByEmail() throws Exception {
        // Arrange
        doNothing()
                .when(userRepository)
                .deleteByEmail(anyString());

        when(userRepository.findByEmail(anyString()))
                .thenReturn(Optional.ofNullable(userTest));

        // Act
        var _boolean = service.deleteByEmail(anyString());

        // Assert
        Assertions.assertTrue(_boolean);
    }

    @Test
    public void shouldFailDeletingAUserByHisEmail() {
        // Arrange
        when(userRepository.findByEmail(anyString()))
                .thenReturn(Optional.empty());

        // Act && Assert
        Assertions.assertThrows(ServerErrorException.class, () -> service.deleteByEmail(anyString()));
    }
}