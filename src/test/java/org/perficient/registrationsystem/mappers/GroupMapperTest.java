package org.perficient.registrationsystem.mappers;

import org.junit.Before;
import org.junit.Test;
import org.perficient.registrationsystem.dto.GroupDto;
import org.perficient.registrationsystem.dto.ProfessorDto;
import org.perficient.registrationsystem.dto.StudentDto;
import org.perficient.registrationsystem.model.Group;
import org.perficient.registrationsystem.model.Professor;
import org.perficient.registrationsystem.model.Student;
import org.perficient.registrationsystem.model.enums.Department;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.sql.Time;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GroupMapperTest {
    private final GroupMapper groupMapper = GroupMapper.INSTANCE;
    private Group group;
    private GroupDto groupDto;

    private Group createGroup() {
        var group1 = new Group();
        group1.setId(1);
        group1.setNumber(1);

        var professor = new Professor();
        professor.setFirstName("Camilo");
        professor.setFirstName("Rincon");
        professor.setDepartment(Department.MATH_DEPARTMENT);

        professor.setPassword("Secret");

        group1.setStartTime(Time.valueOf("13:00:00"));
        group1.setEndTime(Time.valueOf("15:00:00"));

        var students = new HashSet<Student>();
        group1.setProfessor(professor);
        group1.setStudents(students);

        return group1;
    }

    private GroupDto createGroupDto() {
        var groupDto1 = new GroupDto();
        groupDto1.setId(1);
        groupDto1.setNumber(1);

        var professorDto = new ProfessorDto();
        professorDto.setFirstName("Camilo");
        professorDto.setFirstName("Rincon");
        professorDto.setDepartment(Department.MATH_DEPARTMENT);

        professorDto.setPassword(BCrypt.hashpw("Secret", BCrypt.gensalt()));

        groupDto1.setStartTime(Time.valueOf("13:00:00"));
        groupDto1.setEndTime(Time.valueOf("15:00:00"));

        var students = new HashSet<StudentDto>();
        groupDto1.setProfessor(professorDto);
        groupDto1.setStudents(students);
        return groupDto1;
    }

    @Before
    public void setUp() throws Exception {
        group = createGroup();
        groupDto = createGroupDto();
    }

    @Test
    public void groupToGroupDto() {
        var groupDtoUsingMapper = groupMapper.groupToGroupDto(group);

        assertEquals(groupDtoUsingMapper, groupDto);
        assertEquals(groupDtoUsingMapper.getClass().getSimpleName(), "GroupDto");
    }

    @Test
    public void groupDtoToGroup() {
        var groupUsingMapper = groupMapper.groupDtoToGroup(groupDto);

        assertEquals(groupUsingMapper, group);
        assertEquals(groupUsingMapper.getClass().getSimpleName(), "Group");
    }
}