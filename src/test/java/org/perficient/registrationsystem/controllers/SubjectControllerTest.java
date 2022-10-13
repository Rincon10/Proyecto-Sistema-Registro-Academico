package org.perficient.registrationsystem.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.perficient.registrationsystem.dto.SubjectDto;
import org.perficient.registrationsystem.mappers.json.JsonMapper;
import org.perficient.registrationsystem.services.SubjectService;
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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@AutoConfigureJsonTesters
@WebMvcTest(controllers = GroupController.class)
public class SubjectControllerTest {
    private final String PATH = "/api/v1/subjects";

    int expectedOkHttpStatus = HttpStatus.OK.value();
    int expectedNotFoundHttpStatus = HttpStatus.NOT_FOUND.value();
    int expectedCreatedStatus = HttpStatus.CREATED.value();
    int expectedServerErrorStatus = HttpStatus.INTERNAL_SERVER_ERROR.value();
    int expectedForbiddenStatus = HttpStatus.FORBIDDEN.value();

    @Mock
    SubjectService subjectService;

    @InjectMocks
    SubjectController subjectController;

    private MockMvc mockMvc;

    private SubjectDto subjectDtoTest;
    private Set<SubjectDto> dtoSetTest;

    // This object will be initialized thanks to @AutoConfigureJsonTesters
    @Autowired
    private JacksonTester<SubjectDto> dtoJacksonTester;

    @Before
    public void setUp() throws Exception {
        // Initializing Mockito
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(subjectController)
                .setControllerAdvice(new ControllerAdvisor())
                .build();

        // Setting the SubjectDto
        subjectDtoTest = SubjectDto
                .builder()
                .acronym("TEST")
                .name("NameTest")
                .prerequisites(new HashSet<>())
                .build();
        dtoSetTest = new HashSet();

        // Initializing JacksonTester
        ObjectMapper objectMapper = new ObjectMapper();
        JacksonTester.initFields(this, objectMapper);

        // Initializing Controller
        subjectController = new SubjectController(subjectService);
    }

    @Test
    public void getAllSubjects() throws Exception {
        // Arrange
        dtoSetTest.add(subjectDtoTest);
        when(subjectService.getAllSubjects())
                .thenReturn(dtoSetTest);

        // Act
        MockHttpServletResponse response = mockMvc
                .perform(get(String.format("%s", PATH))
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        // Assert
        assertThat(response.getStatus()).isEqualTo(expectedOkHttpStatus);

        SubjectDto[] savedSet = JsonMapper.mapFromJson(response.getContentAsString(), SubjectDto[].class);
        assertThat(savedSet).hasSize(dtoSetTest.size());
        assertEquals(savedSet[0], subjectDtoTest);
    }

    @Test
    public void shouldReturnSubjectByAcronymSuccessfully() throws Exception {
        // Arrange
        String acronym = "TEST";
        when(subjectService.getSubjectByAcronym(acronym))
                .thenReturn(subjectDtoTest);

        // Act
        MockHttpServletResponse response = mockMvc
                .perform(get(String.format("%s/%s", PATH, acronym))
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        // Assert
        assertThat(response.getStatus()).isEqualTo(expectedOkHttpStatus);
        String r = response.getContentAsString();
        assertThat(response.getContentAsString())
                .isEqualTo(dtoJacksonTester.write(subjectDtoTest).getJson());
    }

    @Test
    public void shouldFailSearchingASubjectWithAnInvalidAcronym() throws Exception {
        // Arrange
        String acronym = "NO";

        given(subjectService.getSubjectByAcronym(acronym))
                .willThrow(new ServerErrorException("The Subject that you are looking for doesn't exists.",
                        HttpStatus.NOT_FOUND.value()));

        // Act
        MockHttpServletResponse response = mockMvc
                .perform(get(String.format("%s/%s", PATH, acronym))
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        // Assert
        assertThat(response.getStatus()).isEqualTo(expectedNotFoundHttpStatus);
        assertThat(response.getContentAsString()).contains("The Subject that you are looking for doesn't exists.");
    }

    @Test
    public void addSubject() throws Exception {
        // Arrange
        String jsonSubject = JsonMapper.mapToJson(subjectDtoTest);

        when(subjectService.addSubject(subjectDtoTest))
                .thenReturn(subjectDtoTest);

        // Act
        MockHttpServletResponse response = mockMvc.perform(post(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonSubject))
                .andReturn()
                .getResponse();

        // Assert
        assertThat(response.getStatus()).isEqualTo(expectedCreatedStatus);
        assertThat(response.getContentAsString()).isEqualTo(jsonSubject);
    }

    @Test
    public void updateSubjectByAcronym() throws Exception {
        // Arrange
        String oldName = subjectDtoTest.getName();
        String newName = "New Name";

        var subjectDto = SubjectDto.builder()
                .acronym("TEST")
                .name(newName)
                .build();

        when(subjectService.updateSubjectByAcronym(anyString(), any(SubjectDto.class)))
                .thenReturn(subjectDto);

        String jsonSubject = JsonMapper.mapToJson(subjectDto);

        // Act

        MockHttpServletResponse response = mockMvc.perform(put(String.format("%s/%s", PATH, subjectDtoTest.getAcronym()))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonSubject))
                .andReturn()
                .getResponse();


        // Assert
        String r = response.getContentAsString();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.ACCEPTED.value());
        SubjectDto updatedSubject = JsonMapper.mapFromJson(r, SubjectDto.class);

        assertNotEquals(oldName, updatedSubject.getAcronym());
    }

    @Test
    public void shouldFailUpdateASubjectThatNotExits() throws Exception {
        // Arrange
        String acronym = "NOE";
        String jsonSubject = JsonMapper.mapToJson(subjectDtoTest);

        given(subjectService.updateSubjectByAcronym(acronym, subjectDtoTest))
                .willThrow(new ServerErrorException("The Subject that you are looking for doesn't exists.",
                        HttpStatus.NOT_FOUND.value()));

        // Act
        MockHttpServletResponse response = mockMvc.perform(put(String.format("%s/%s", PATH, acronym))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonSubject))
                .andReturn()
                .getResponse();

        // Assert
        // Assert
        assertThat(response.getStatus()).isEqualTo(expectedNotFoundHttpStatus);
        assertThat(response.getContentAsString()).contains("The Subject that you are looking for doesn't exists.");
    }

    @Test
    public void deleteByAcronym() throws Exception {
        // Arrange
        String acronym = "SOME";
        when(subjectService.deleteSubjectByAcronym(acronym))
                .thenReturn(true);

        // Act
        MockHttpServletResponse response = mockMvc.perform(delete(String.format("%s/%s", PATH, acronym)))
                .andReturn()
                .getResponse();

        // Assert
        assertThat(response.getContentAsString())
                .isEqualTo("true");
    }

    @Test
    public void shouldFailDeletingASubjectThatDoesntExists() throws Exception {
        // Arrange
        String acronym = "NOE";

        given(subjectService.deleteSubjectByAcronym(acronym))
                .willThrow(new ServerErrorException("The Subject that you are looking for doesn't exists.",
                        HttpStatus.NOT_FOUND.value()));

        // Act
        MockHttpServletResponse response = mockMvc.perform(delete(String.format("%s/%s", PATH, acronym))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn()
                .getResponse();

        // Assert
        // Assert
        assertThat(response.getStatus()).isEqualTo(expectedNotFoundHttpStatus);
        assertThat(response.getContentAsString()).contains("The Subject that you are looking for doesn't exists.");
    }
}