package org.perficient.registrationsystem.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.perficient.registrationsystem.dto.GroupDto;
import org.perficient.registrationsystem.mappers.GroupMapper;
import org.perficient.registrationsystem.model.Group;
import org.perficient.registrationsystem.model.Professor;
import org.perficient.registrationsystem.model.enums.Department;
import org.perficient.registrationsystem.repositories.GroupRepository;
import org.perficient.registrationsystem.services.exceptions.ServerErrorException;
import org.perficient.registrationsystem.services.impl.GroupServiceImpl;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class GroupServiceImplTest {
    private final GroupMapper mapper = GroupMapper.INSTANCE;
    @Mock
    private GroupRepository groupRepository;
    private GroupServiceImpl groupService;

    private Group group;

    private GroupDto groupDto;


    @Before
    public void setUp() throws Exception {
        var professor = Professor
                .builder()
                .department(Department.MATH_DEPARTMENT)
                .build();

        group = Group.builder()
                .id(1)
                .startTime(Time.valueOf("13:00:00"))
                .endTime(Time.valueOf("15:00:00"))
                .professor(professor)
                .build();
        groupDto = mapper.groupToGroupDto(group);

        groupService = new GroupServiceImpl(groupRepository, GroupMapper.INSTANCE);
    }

    @Test
    public void get_all_groups() {
        List<Group> groups = new ArrayList<>();
        groups.add(group);


        // Arrange
        when(groupRepository.findAll())
                .thenReturn(groups);

        // Act
        Set<GroupDto> set = groupService.getAllGroups();

        // Assert
        assertThat(set.size()).isEqualTo(1);
        assertThat(set.toArray()[0])
                .usingRecursiveComparison().isEqualTo(mapper.groupToGroupDto(groups.get(0)));

        verify(groupRepository, times(1)).findAll();
        verifyNoMoreInteractions(groupRepository);
    }

    @Test
    public void should_find_group_given_an_valid_id() throws Exception {
        // Arrange
        when(groupRepository.findById(anyInt()))
                .thenReturn(Optional.ofNullable(group));

        // Act
        GroupDto group1 = groupService.findGroupById(anyInt());

        // Assert
        assertThat(group).usingRecursiveComparison().isEqualTo(mapper.groupDtoToGroup(group1));
        verify(groupRepository, times(1)).findById(anyInt());
        verifyNoMoreInteractions(groupRepository);

    }

    @Test
    public void should_not_fond_a_group_given_an_invalid_id() throws Exception {
        // Arrange
        when(groupRepository.findById(anyInt()))
                .thenReturn(Optional.empty());

        // Act
        Assertions.assertThrows(ServerErrorException.class, () -> groupService.findGroupById(anyInt()));

        // Assert
        verify(groupRepository, times(1)).findById(anyInt());
        verifyNoMoreInteractions(groupRepository);
    }


    @Test
    public void should_add_new_group() throws Exception {
        // Arrange
        when(groupRepository.save(any(Group.class))).thenReturn(group);

        // Act
        final var saved = groupService.addGroup(groupDto);

        // Assert
        assertThat(saved).isEqualTo(true);
        verify(groupRepository, times(1))
                .save(any(Group.class));
        verifyNoMoreInteractions(groupRepository);

    }

    @Test(expected = Exception.class)
    public void should_fail_adding_a_new_group_for_duplicated_id() throws Exception {
        // Arrange
        when(groupRepository.save(any(Group.class)))
                .thenThrow(ServerErrorException.class);

        // Act
        final var saved = groupService.addGroup(groupDto);

        // Assert
        // we verify that Happened an exception
        assertThat(saved).isEqualTo(false);
    }

    @Test
    public void should_update_a_group_given_a_valid_id() throws Exception {
        // Arrange
        when(groupRepository.findById(anyInt()))
                .thenReturn(Optional.ofNullable(group));

        when(groupRepository.save(any(Group.class)))
                .thenReturn(group);

        GroupDto groupDto1 = groupService.findGroupById(anyInt());

        // Act
        GroupDto updatedGroup =  groupService.updateGroupById(groupDto1.getId(), groupDto);

        // Assert
        verify(groupRepository, times(1))
                .save(any(Group.class));
    }

    @Test
    public void delete_a_group_given_a_valid_id() throws Exception {
        // Arrange
        doNothing()
                .when(groupRepository)
                .deleteById(anyInt());

        when(groupRepository.findById(anyInt()))
                .thenReturn(Optional.of(group));

        // Act
        groupService.deleteGroupById(anyInt());

        // Assert
        verify(groupRepository,times(1))
                .deleteById(anyInt());
    }

    @Test(expected = Exception.class)
    public void should_throw_an_exception_deleting_a_group_given_a_invalid_id() throws Exception {
        // Act
        groupService.deleteGroupById(anyInt());

        // Assert
        verify(groupRepository, times(1))
                .deleteById(anyInt());
        verifyNoMoreInteractions(groupRepository);

    }
}