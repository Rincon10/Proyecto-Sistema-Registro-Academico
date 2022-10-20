package org.perficient.registrationsystem.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.perficient.registrationsystem.dto.UserDto;
import org.perficient.registrationsystem.mappers.json.JsonMapper;
import org.perficient.registrationsystem.services.UserService;
import org.perficient.registrationsystem.services.exceptions.ControllerAdvisor;
import org.perficient.registrationsystem.services.exceptions.ServerErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@AutoConfigureJsonTesters
@WebMvcTest(controllers = GroupController.class)
public class UserControllerTest {
    private final String PATH = "/api/v1/users";

    int expectedOkHttpStatus = HttpStatus.OK.value();
    int expectedNotFoundHttpStatus = HttpStatus.NOT_FOUND.value();
    int expectedCreatedStatus = HttpStatus.CREATED.value();
    int expectedAcceptedStatus = HttpStatus.ACCEPTED.value();
    int expectedServerErrorStatus = HttpStatus.INTERNAL_SERVER_ERROR.value();
    int expectedForbiddenStatus = HttpStatus.FORBIDDEN.value();

    @Mock
    UserService service;

    @InjectMocks
    UserController userController;

    private MockMvc mockMvc;

    private UserDto userDtoTest;
    private Set<UserDto> userDtoTestSet;

    @Autowired
    private JacksonTester<UserDto> dtoJacksonTester;

    @Before
    public void setUp() throws Exception {
        // Initializing Mockito
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .setControllerAdvice(new ControllerAdvisor())
                .build();

        // Setting the SubjectDto
        userDtoTest = UserDto
                .builder()
                .firstName("Ivan Camilo")
                .lastName("Rincon Saavedra")
                .email("ivan.rincon-s@mail.escuelaing.edu.co")
                .password("123456")
                .build();

        userDtoTestSet = new HashSet();

        // Initializing JacksonTester
        ObjectMapper objectMapper = new ObjectMapper();
        JacksonTester.initFields(this, objectMapper);

        // Initializing Controller
        userController = new UserController(service);
    }

    @Test
    public void getAllUsers() throws Exception {
        // Arrange
        userDtoTestSet.add(userDtoTest);
        when(service.getAllUsers())
                .thenReturn(userDtoTestSet);

        // Act
        MockHttpServletResponse response = mockMvc
                .perform(get(String.format("%s", PATH))
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        // Assert
        assertThat(response.getStatus()).isEqualTo(expectedOkHttpStatus);

        UserDto[] savedSet = JsonMapper.mapFromJson(response.getContentAsString(), UserDto[].class);
        assertThat(savedSet).hasSize(userDtoTestSet.size());
        assertThat(savedSet[0]).isEqualTo(userDtoTest);
    }

    @Test
    public void getUserByIdSuccessfully() throws Exception {
        // Arrange
        int id = 1;
        when(service.getUserById(anyInt()))
                .thenReturn(userDtoTest);

        // Act
        MockHttpServletResponse response = mockMvc
                .perform(get(String.format("%s/id/%s", PATH, id))
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        // Assert
        assertThat(response.getStatus()).isEqualTo(expectedOkHttpStatus);
        String r = response.getContentAsString();
        assertThat(response.getContentAsString())
                .isEqualTo(dtoJacksonTester.write(userDtoTest).getJson());
    }

    @Test
    public void shouldGetNotFoundResponseForInvalidId() throws Exception {
        int id = 1;
        given(service.getUserById(anyInt()))
                .willThrow(new ServerErrorException("The User that you are looking for doesn't exists.",
                        HttpStatus.NOT_FOUND.value()));

        // Act
        MockHttpServletResponse response = mockMvc
                .perform(get(String.format("%s/id/%s", PATH, id))
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        // Assert
        assertThat(response.getStatus()).isEqualTo(expectedNotFoundHttpStatus);
        assertThat(response.getContentAsString())
                .contains("doesn't exists.");
    }

    @Test
    public void getUserByEmail() throws Exception {
        // Arrange
        String email = "TestEmail";
        when(service.getUserByEmail(anyString())).
                thenReturn(userDtoTest);

        // Act
        MockHttpServletResponse response = mockMvc
                .perform(get(String.format("%s/%s", PATH, email))
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        // Assert
        assertThat(response.getStatus()).isEqualTo(expectedOkHttpStatus);
        assertThat(response.getContentAsString())
                .isEqualTo(dtoJacksonTester.write(userDtoTest).getJson());
    }

    @Test
    public void shouldFailSearchingByAInvalidEmail() throws Exception {
        // Arrange
        String email = "TestEmail";
        given(service.getUserByEmail(anyString()))
                .willThrow(new ServerErrorException("The User that you are looking for doesn't exists.",
                        expectedNotFoundHttpStatus));

        // Act
        MockHttpServletResponse response = mockMvc
                .perform(get(String.format("%s/%s", PATH, email))
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        // Assert
        assertThat(response.getStatus()).isEqualTo(expectedNotFoundHttpStatus);

        assertThat(response.getContentAsString())
                .contains("doesn't exists.");
    }

    @Test
    public void addUser() throws Exception {
        // Arrange
        String jsonUser = JsonMapper.mapToJson(userDtoTest);
        when(service.addUser(any(UserDto.class))).
                thenReturn(userDtoTest);

        // Act
        MockHttpServletResponse response = mockMvc
                .perform(post(String.format("%s", PATH))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUser))
                .andReturn()
                .getResponse();

        // Assert
        assertThat(response.getStatus()).isEqualTo(expectedCreatedStatus);
        assertThat(response.getContentAsString()).isEqualTo(jsonUser);
    }

    @Test
    public void shouldFailAddingAUserThatAlreadyExists() throws Exception {
        // Arrange
        String jsonUser = JsonMapper.mapToJson(userDtoTest);
        given(service.addUser(any(UserDto.class)))
                .willThrow(new ServerErrorException("User already exits!", HttpStatus.FORBIDDEN.value()));

        // Act
        MockHttpServletResponse response = mockMvc
                .perform(post(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUser))
                .andReturn()
                .getResponse();
        var s = response.getContentAsString();

        // Assert
        assertThat(response.getStatus()).isEqualTo(expectedForbiddenStatus);
        assertThat(response.getContentAsString())
                .contains("User already exits!");
    }

    @Test
    public void updateUserByEmail() throws Exception {
        // Arrange
        var oldPassword = userDtoTest.getPassword();
        var newPassword = "1111111";

        var userDto = UserDto
                .builder()
                .email(userDtoTest.getEmail())
                .password(newPassword)
                .build();

        when(service.updateUserByEmail(anyString(), any(UserDto.class)))
                .thenReturn(userDto);

        var jsonUser = JsonMapper.mapToJson(userDto);

        // Act

        MockHttpServletResponse response = mockMvc.perform(put(String.format("%s/%s", PATH, userDtoTest.getEmail()))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonUser))
                .andReturn()
                .getResponse();

        // Assert
        String r = response.getContentAsString();
        assertThat(response.getStatus()).isEqualTo(expectedAcceptedStatus);
        UserDto updatedUserDto = JsonMapper.mapFromJson(r, UserDto.class);

        assertNotEquals(oldPassword, updatedUserDto.getPassword());
    }

    @Test
    public void shouldResponseABadRequestForNullEmail() throws Exception {
        // Arrange
        var newPassword = "1111111";

        var userDto = UserDto
                .builder()
                .password(newPassword)
                .build();

        when(service.updateUserByEmail(anyString(), any(UserDto.class)))
                .thenReturn(userDto);

        var jsonUser = JsonMapper.mapToJson(userDto);

        // Act

        MockHttpServletResponse response = mockMvc.perform(put(String.format("%s/%s", PATH, userDtoTest.getEmail()))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonUser))
                .andReturn()
                .getResponse();

        // Assert
        String responseContent = response.getContentAsString();
        assertThat(responseContent)
                .contains("The field email must not be null");
    }

    @Test
    public void deleteUserByEmail() throws Exception {
        // Arrange
        String email = "someEmail@gmail.com";
        when(service.deleteByEmail(anyString()))
                .thenReturn(true);

        // Act
        MockHttpServletResponse response = mockMvc.perform(delete(String.format("%s/%s", PATH, email)))
                .andReturn()
                .getResponse();

        // Assert
        assertThat(response.getContentAsString())
                .isEqualTo("true");
    }
}