package org.perficient.registrationsystem.mappers;

import org.junit.Before;
import org.junit.Test;
import org.perficient.registrationsystem.dto.SubjectDto;
import org.perficient.registrationsystem.model.Subject;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SubjectMapperTest {

    private final SubjectMapper subjectMapper = SubjectMapper.INSTANCE;
    private Subject testSubject;
    private SubjectDto testSubjectDto;

    private Subject createSubject() {
        Set<Subject> prerequisites = new HashSet<>();
        prerequisites.add(new Subject("TEST1", "Some Test1", new HashSet<>()));
        prerequisites.add(new Subject("TEST2", "Some Test2", new HashSet<>()));

        var subject = Subject
                .builder()
                .acronym("TEST")
                .name("Some test")
                .prerequisites(prerequisites)
                .build();

        return subject;
    }

    private SubjectDto createSubjectDto() {
        Set<SubjectDto> prerequisites = new HashSet<>();
        prerequisites.add(new SubjectDto("TEST1", "Some Test1", new HashSet<>()));
        prerequisites.add(new SubjectDto("TEST2", "Some Test2", new HashSet<>()));

        var subjectDto = SubjectDto
                .builder()
                .acronym("TEST")
                .name("Some test")
                .prerequisites(prerequisites)
                .build();

        return subjectDto;
    }

    @Before
    public void setUp() {
        testSubject = createSubject();
        testSubjectDto = createSubjectDto();
    }

    @Test
    public void subjectSetToSubjectDtoSet() {
        var subjectDtoSetUsingMapper = subjectMapper
                .subjectSetToSubjectDtoSet(testSubject.getPrerequisites());

        assertThat(subjectDtoSetUsingMapper)
                .usingRecursiveComparison()
                .isEqualTo(testSubjectDto.getPrerequisites());
    }

    @Test
    public void subjectDtoSetToSubjectSet() {
        var subjectSetUsingMapper = subjectMapper
                .subjectDtoSetToSubjectSet(testSubjectDto.getPrerequisites());

        assertThat(subjectSetUsingMapper)
                .usingRecursiveComparison()
                .isEqualTo(testSubject.getPrerequisites());

    }

    @Test
    public void subjectToSubjectDto() {
        var subjectUsingMapper = subjectMapper.subjectToSubjectDto(testSubject);

        assertEquals(subjectUsingMapper, testSubjectDto);
        assertEquals(subjectUsingMapper.getClass().getSimpleName(), "SubjectDto");
    }

    @Test
    public void subjectDtoToSubject() {
        var subjectUsingMapper = subjectMapper.subjectDtoToSubject(testSubjectDto);

        assertEquals(subjectUsingMapper, testSubject);
        assertEquals(subjectUsingMapper.getClass().getSimpleName(), "Subject");
    }
}