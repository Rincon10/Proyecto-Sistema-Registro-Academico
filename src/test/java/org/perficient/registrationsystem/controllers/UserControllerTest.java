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
//    int expectedServerErrorStatus = HttpStatus.INTERNAL_SERVER_ERROR.value();
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
                .id(100L)
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
        // We Mock the service when we call the getAllUser is going to return a set of one user.
        when(service.getAllUsers(0, 5))
                .thenReturn(userDtoTestSet);

        // Act
        // We made a get request to the controller
        MockHttpServletResponse response = mockMvc
                .perform(get(String.format("%s", PATH))
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        // Assert
        // We assert that the response was successfully
        assertThat(response.getStatus()).isEqualTo(expectedOkHttpStatus);

        // We extract the body response from the response
        UserDto[] savedSet = JsonMapper.mapFromJson(response.getContentAsString(), UserDto[].class);

        // Verifying that the Mocked set has the same siza that the set of the body response
        assertThat(savedSet).hasSize(userDtoTestSet.size());
        // Both elements on the first position are the same.
        assertThat(savedSet[0]).isEqualTo(userDtoTest);
    }

    @Test
    public void getUserByIdSuccessfully() throws Exception {
        // Arrange
        int id = 1;
        // Mocking that if we send any id is going to return successfully a user
        when(service.getUserById(anyInt()))
                .thenReturn(userDtoTest);

        // Act
        // We made a get request to the controller with any id
        MockHttpServletResponse response = mockMvc
                .perform(get(String.format("%s/id/%s", PATH, id))
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        // Assert
        // We assert that the response was successfully
        assertThat(response.getStatus()).isEqualTo(expectedOkHttpStatus);

        String r = response.getContentAsString();
        // Verifying that the expected user is the same of the body response of the Servlet
        assertThat(response.getContentAsString())
                .isEqualTo(dtoJacksonTester.write(userDtoTest).getJson());
    }

    @Test
    public void shouldGetNotFoundResponseForInvalidId() throws Exception {
        // Arrange
        int id = 1;
        // Mocking that if we send any id is going to return a Server Exception
        given(service.getUserById(anyInt()))
                .willThrow(new ServerErrorException("The User that you are looking for doesn't exists.",
                        HttpStatus.NOT_FOUND.value()));

        // Act
        // We made a get request to the controller with any id
        MockHttpServletResponse response = mockMvc
                .perform(get(String.format("%s/id/%s", PATH, id))
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        // Assert
        // We check that the status code of our response is 404 saying us that was not found
        assertThat(response.getStatus()).isEqualTo(expectedNotFoundHttpStatus);

        // We verify that the body response contains the words doesn't exist
        assertThat(response.getContentAsString())
                .contains("doesn't exists.");
    }

    @Test
    public void gettingTheUserByEmailForValidEmail() throws Exception {
        // Arrange
        // We transform our userDtoTest to a json Format
        String jsonUser = JsonMapper.mapToJson(userDtoTest);
        // Mocks the service to receive a valid userDto
        when(service.getUserByEmail(anyString())).
                thenReturn(userDtoTest);

        // Act
        // We made a post request to the controller with any email
        MockHttpServletResponse response = mockMvc
                .perform(post(String.format("%s/email", PATH))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUser))
                .andReturn()
                .getResponse();

        // Assert
        // We assert that the response was successfully
        assertThat(response.getStatus()).isEqualTo(expectedOkHttpStatus);
        // Verifying that the expected user is the same of the body response of the Servlet
        assertThat(response.getContentAsString())
                .isEqualTo(dtoJacksonTester.write(userDtoTest).getJson());
    }

    @Test
    public void shouldFailSearchingByAInvalidEmail() throws Exception {
        // Arrange
        // We transform our userDtoTest to a json Format
        String jsonUser = JsonMapper.mapToJson(userDtoTest);

        // We made a get request to the controller with any email
        given(service.getUserByEmail(anyString()))
                .willThrow(new ServerErrorException("The User that you are looking for doesn't exists.",
                        expectedNotFoundHttpStatus));

        // Act
        MockHttpServletResponse response = mockMvc
                .perform(post(String.format("%s/email", PATH))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUser))
                .andReturn()
                .getResponse();

        // Assert
        // We check that the status code of our response is 404 saying us that was not found
        assertThat(response.getStatus()).isEqualTo(expectedNotFoundHttpStatus);
        // We verify that the body response contains the words doesn't exist
        assertThat(response.getContentAsString())
                .contains("doesn't exists.");
    }

    @Test
    public void shouldAddUser() throws Exception {
        // Arrange
        // Create a user in a JSON format
        String jsonUser = JsonMapper.mapToJson(userDtoTest);
        when(service.addUser(any(UserDto.class))).
                thenReturn(userDtoTest);

        // Act
        // We sent the post request to add the user
        MockHttpServletResponse response = mockMvc
                .perform(post(String.format("%s", PATH))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUser))
                .andReturn()
                .getResponse();

        // Assert
        // We verify that the status response was "CREATED"
        assertThat(response.getStatus()).isEqualTo(expectedCreatedStatus);
        // We compare the body response with the expected user
        assertThat(response.getContentAsString()).isEqualTo(jsonUser);
    }

    @Test
    public void shouldFailAddingAUserThatAlreadyExists() throws Exception {
        // Arrange
        // We transform the userDto in JSON format
        String jsonUser = JsonMapper.mapToJson(userDtoTest);
        given(service.addUser(any(UserDto.class)))
                .willThrow(new ServerErrorException("User already exits!", HttpStatus.FORBIDDEN.value()));

        // Act
        // We perform the POST Request with the user
        MockHttpServletResponse response = mockMvc
                .perform(post(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUser))
                .andReturn()
                .getResponse();
        var s = response.getContentAsString();

        // Assert
        assertThat(response.getStatus()).isEqualTo(expectedForbiddenStatus);
        // We expect a the message User already exits in the body of the response
        assertThat(response.getContentAsString())
                .contains("User already exits!");
    }

    @Test
    public void updateUserById() throws Exception {
        // Arrange
        var oldPassword = userDtoTest.getPassword();
        var newPassword = "1111111";

        var userDto = UserDto
                .builder()
                .id(userDtoTest.getId())
                .password(newPassword)
                .email("test@test.com")
                .build();

        when(service.updateUserById(anyInt(), any(UserDto.class)))
                .thenReturn(userDto);
        // Mapping the expected object in a JSON Format
        var jsonUser = JsonMapper.mapToJson(userDto);

        // Act
        // Making a Put Request to the controller
        MockHttpServletResponse response = mockMvc.perform(put(PATH + "/" + userDtoTest.getId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonUser))
                .andReturn()
                .getResponse();

        // Assert
        // Saving the response body
        String r = response.getContentAsString();
        assertThat(response.getStatus()).isEqualTo(expectedAcceptedStatus);
        // Transforming out json body in the object UserDto
        UserDto updatedUserDto = JsonMapper.mapFromJson(r, UserDto.class);
        // Verifying that the oldPassword is different of the updated user
        assertNotEquals(oldPassword, updatedUserDto.getPassword());
    }

    @Test
    public void shouldResponseABadRequestForNullEmail() throws Exception {
        // Arrange
        var newPassword = "1111111";

        var userDto = UserDto
                .builder()
                .password(newPassword)
                .email(null)
                .build();

        when(service.updateUserById(anyInt(), any(UserDto.class)))
                .thenReturn(userDto);

        // Mapping the UserObject into json format
        var jsonUser = JsonMapper.mapToJson(userDto);

        // Act
        // Trying to perform a put request
        MockHttpServletResponse response = mockMvc.perform(put(PATH + "/" + userDtoTest.getId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonUser))
                .andReturn()
                .getResponse();

        // Assert
        String responseContent = response.getContentAsString();
        // The response error expected, is that the email must not be null
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