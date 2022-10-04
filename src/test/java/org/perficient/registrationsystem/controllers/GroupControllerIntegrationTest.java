package org.perficient.registrationsystem.controllers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.perficient.registrationsystem.dto.GroupDto;
import org.perficient.registrationsystem.services.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = GroupController.class)
public class GroupControllerTest {
    private final String PATH = "/api/v1/groups";

    @Autowired
    MockMvc mockMvc;

    GroupDto groupDto;

    @Mock
    GroupService groupService;

    @InjectMocks
    GroupController groupController;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        // Setting a new GroupDto
        groupDto = new GroupDto();
        groupDto.setId(1);
        groupDto.setNumber(1);

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

            // then
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void getGroupByIdWhenExists() throws Exception {
        //given
        given(groupService.findGroupById(1))
                .willReturn(groupDto);

        //when
        MockHttpServletResponse response = mockMvc.perform(get(PATH))
                .andExpect(status().isOk())
                .andExpect(responseBody())


        //then


    }

    @Test
    public void addGroup() {
    }

    @Test
    public void updateGroupById() {
    }

    @Test
    public void deleteById() {
    }

    @Test
    public void defaultHandlerException() {
    }
}