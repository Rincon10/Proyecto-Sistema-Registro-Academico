package org.perficient.registrationsystem.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.perficient.registrationsystem.dto.GroupDto;
import org.perficient.registrationsystem.dto.ProfessorDto;
import org.perficient.registrationsystem.dto.SubjectDto;
import org.perficient.registrationsystem.mappers.json.JsonMapper;
import org.perficient.registrationsystem.model.enums.Department;
import org.perficient.registrationsystem.services.GroupService;
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

import java.sql.SQLException;
import java.sql.Time;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@AutoConfigureJsonTesters
@WebMvcTest(controllers = GroupController.class)
public class GroupControllerTest {
    private final String PATH = "/api/v1/groups";

    @Mock
    GroupService groupService;

    @InjectMocks
    GroupController groupController;

    private MockMvc mockMvc;
    private GroupDto groupDto;

    // This object will be initialized thanks to @AutoConfigureJsonTesters
    @Autowired
    private JacksonTester<GroupDto> dtoJacksonTester;

    private GroupDto createAGroupDto() {
        // Setting a new GroupDto
        groupDto = new GroupDto();
        groupDto.setId(1);
        groupDto.setNumber(1);

        ProfessorDto professorDto = new ProfessorDto();
        professorDto.setFirstName("leidy");
        professorDto.setLastName("rincon");
        professorDto.setEmail("leidy.rincon-s@mail.escuelaing.edu.co");
        professorDto.setDepartment(Department.MATH_DEPARTMENT);

        SubjectDto subjectDto = new SubjectDto();
        subjectDto.setAcronym("MATD");
        subjectDto.setName("Matematicas Discretas");

        groupDto.setProfessor(professorDto);
        groupDto.setSubject(subjectDto);

        groupDto.setStartTime(Time.valueOf("13:00:00"));
        groupDto.setEndTime(Time.valueOf("15:00:00"));

        return groupDto;
    }

    @Before
    public void setUp() throws Exception {
        // Initializing Mockito
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(groupController)
                .setControllerAdvice(new ControllerAdvisor())
                .build();

        // Initializing JacksonTester
        ObjectMapper objectMapper = new ObjectMapper();
        JacksonTester.initFields(this, objectMapper);

        groupDto = createAGroupDto();

        // Initializing Controller
        groupController = new GroupController(groupService);
    }

    @Test
    public void getAllGroups() {
        try {
            // given
            Set<GroupDto> groupDtoSet = new HashSet<>();
            groupDtoSet.add(groupDto);

            // when
            when(groupService.getAllGroups()).thenReturn(groupDtoSet);

            MockHttpServletResponse response = mockMvc
                    .perform(get(String.format("%s", PATH))
                            .accept(MediaType.APPLICATION_JSON))
                    .andReturn()
                    .getResponse();

            // then
            assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

            GroupDto[] savedSet = JsonMapper.mapFromJson(response.getContentAsString(), GroupDto[].class);
            assertThat(savedSet.length).isEqualTo(groupDtoSet.size());
            assertEquals(savedSet[0], groupDto);

        } catch (SQLException e) {
            fail("failed trying to get All the groups");
        } catch (Exception e) {
            fail("failed trying to get All the groups");
        }
    }

    @Test
    public void getGroupByIdWhenExists() throws Exception {
        //given
        Integer id = 1;

        //when
        when(groupService.findGroupById(id))
                .thenReturn(groupDto);

        MockHttpServletResponse response = mockMvc
                .perform(get(String.format("%s/%s", PATH, id))
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        //then

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        assertThat(response.getContentAsString())
                .isEqualTo(dtoJacksonTester.write(groupDto).getJson());
    }

    @Test
    public void addGroupWithoutError() throws Exception {
        // given
        Integer number = 5;
        groupDto.setId(number);
        groupDto.setNumber(number);

        String jsonGroup = JsonMapper.mapToJson(groupDto);

        // when
        when(groupService.addGroup(groupDto)).thenReturn(true);

        MockHttpServletResponse response = mockMvc.perform(post(PATH)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonGroup))
                .andReturn()
                .getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());

        assertThat(response.getContentAsString())
                .isEqualTo("true");
    }

    @Test
    public void updateGroupByIdWhenExists() throws Exception {
        // given
        Integer id = 1;

        Time oldTime = groupDto.getStartTime();
        groupDto.setStartTime(Time.valueOf("00:00:00"));

        String jsonGroup = JsonMapper.mapToJson(groupDto);

        // when
        when(groupService.updateGroupById(id, groupDto)).thenReturn(groupDto);

        MockHttpServletResponse response = mockMvc.perform(put(String.format("%s/%s", PATH, id))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonGroup))
                .andReturn()
                .getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.ACCEPTED.value());

        GroupDto updatedGroup = JsonMapper.mapFromJson(response.getContentAsString(), GroupDto.class);

        assertNotEquals(oldTime.toString(), updatedGroup.getStartTime().toString());
    }


    @Test
    public void shouldFailUpdateGroupByIdWhenNotExists() throws Exception {
        // given
        Integer id = -1;
        groupDto.setId(id);

        given(groupService.updateGroupById(id, groupDto))
                .willThrow(new ServerErrorException(ServerErrorException.DOESNT_EXITS, HttpStatus.NOT_FOUND.value()));

        String jsonGroup = JsonMapper.mapToJson(groupDto);

        MockHttpServletResponse response = mockMvc.perform(put(String.format("%s/%s", PATH, id))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonGroup))
                .andReturn()
                .getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void deleteByIdWhenIdExists() throws Exception {
        // given
        Integer id = 1;

        // when
        when(groupService.deleteGroupById(id)).thenReturn(true);

        MockHttpServletResponse response = mockMvc.perform(delete(String.format("%s/%s", PATH, id)))
                .andReturn()
                .getResponse();

        assertThat(response.getContentAsString())
                .isEqualTo("true");
    }

    @Test
    public void shouldFailDeletingByIdWhenIdDoesntExists() throws Exception {
        // given
        Integer id = -100;

        // when
        given(groupService.deleteGroupById(id))
                .willThrow(new ServerErrorException(ServerErrorException.DOESNT_EXITS, HttpStatus.NOT_FOUND.value()));

        MockHttpServletResponse response = mockMvc.perform(delete(String.format("%s/%s", PATH, id)))
                .andReturn()
                .getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }
}