package org.perficient.registrationsystem.repositories;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.runner.RunWith;
import org.perficient.registrationsystem.model.User;
import org.perficient.registrationsystem.services.exceptions.ServerErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;

@RunWith(SpringRunner.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    private User userTest;

    @Before
    public void setUp() throws Exception {
        userTest = User.builder()
                .firstName("Ivan Camilo")
                .lastName("Rincon Saavedra")
                .email("ivan.rincon-s@mail.escuelaing.edu.co")
                .build();
        userTest.setPassword("123456");
    }

    @Test
    public void should_not_find_users_if_repository_is_empty() {
        // Arrange & Act
        Iterable<User> users = userRepository.findAll();

        // Assert
        assertThat(users).isEmpty();
    }

    @Test
    public void should_add_a_user() {
        // Arrange
        int expectedUsers = 1;

        // Act
        var savedUser = userRepository.save(userTest);

        Iterable<User> users = userRepository.findAll();
        // Assert
        assertThat(users).hasSize(expectedUsers);
        assertThat(savedUser)
                .usingRecursiveComparison()
                .isEqualTo(userTest);
    }

    @Test
    public void find_subject_when_email_exits() throws ServerErrorException {
        // Arrange
        String email = userTest.getEmail();
        userRepository.save(userTest);

        // Act
        var userFound = userRepository.findByEmail(email)
                .orElseThrow();

        // Assert
        assertThat(userFound)
                .usingRecursiveComparison()
                .isEqualTo(userTest);
    }

    @Test(expected = Exception.class)
    public void should_not_found_a_user_if_the_email_doesnt_exits() {
        // Arrange
        var email = userTest.getEmail();

        // Act && Assert
        userRepository.findByEmail(email)
                .orElseThrow();

    }

    @Test
    public void should_delete_email_given_a_valid_email() {
        // Arrange
        int size = 1;
        String email = userTest.getEmail();

        // Act
        userRepository.save(userTest);
        Iterable<User> users = userRepository.findAll();

        userRepository.deleteByEmail(email);
        Iterable<User> lastUsers = userRepository.findAll();

        // Assert
        assertThat(lastUsers).isEmpty();
        assertThat(users).hasSize(size);
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void should_fail_deleting_a_invalid_id() {
        // Arrange
        Long id = anyLong();

        // Act
        userRepository
                .deleteById(id);

        // Assert
        // We expect an exception
    }
}