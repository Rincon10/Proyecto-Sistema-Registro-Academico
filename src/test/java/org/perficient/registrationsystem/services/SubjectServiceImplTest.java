package org.perficient.registrationsystem.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.perficient.registrationsystem.dto.SubjectDto;
import org.perficient.registrationsystem.mappers.SubjectMapper;
import org.perficient.registrationsystem.model.Subject;
import org.perficient.registrationsystem.repositories.SubjectRepository;
import org.perficient.registrationsystem.services.exceptions.ServerErrorException;
import org.perficient.registrationsystem.services.impl.SubjectServiceImpl;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SubjectServiceImplTest {

    private final SubjectMapper subjectMapper = SubjectMapper.INSTANCE;

    @Mock
    private SubjectRepository subjectRepository;
    private SubjectServiceImpl subjectService;

    private Subject subjectTest;
    private SubjectDto subjectDtoTest;

    @Before
    public void setUp() throws Exception {
        subjectTest = Subject
                .builder()
                .acronym("TEST")
                .name("Subject Test")
                .prerequisites(new HashSet<>())
                .build();
        subjectDtoTest = subjectMapper.subjectToSubjectDto(subjectTest);

        subjectService = new SubjectServiceImpl(subjectRepository, subjectMapper);

    }

    @Test
    public void getAllSubjects() {
        // Arrange
        int expectedSize = 1;

        List<Subject> subjectList = new ArrayList<>();
        subjectList.add(subjectTest);

        when(subjectRepository.findAll())
                .thenReturn(subjectList);

        // Act

        Set<SubjectDto> set = subjectService.getAllSubjects();

        // Assert
        assertThat(set.size()).isEqualTo(expectedSize);
        assertThat(set.toArray()[0])
                .usingRecursiveComparison().isEqualTo(subjectDtoTest);

        verify(subjectRepository, times(expectedSize))
                .findAll();
        verifyNoMoreInteractions(subjectRepository);
    }

    @Test
    public void getSubjectByAcronym() throws Exception {
        // Arrange
        when(subjectRepository.findById(anyString()))
                .thenReturn(Optional.ofNullable(subjectTest));

        // Act
        SubjectDto dto = subjectService.getSubjectByAcronym(anyString());

        // Assert
        assertThat(dto).usingRecursiveComparison().isEqualTo(subjectDtoTest);
        verify(subjectRepository, times(1))
                .findById(anyString());
        verifyNoMoreInteractions(subjectRepository);
    }

    @Test
    public void getAnExceptionWhenTheSubjectDoesntExists() {
        // Arrange
        when(subjectRepository.findById(anyString()))
                .thenReturn(Optional.empty());

        // Act
        Assertions.assertThrows(ServerErrorException.class, () -> subjectService.getSubjectByAcronym(anyString()));

        // Assert
        verify(subjectRepository, times(1)).findById(anyString());
        verifyNoMoreInteractions(subjectRepository);
    }

    @Test
    public void addSubjectSuccessfully() throws Exception {
        // Arrange
        when(subjectRepository.findById(anyString()))
                .thenReturn(Optional.empty());

        when(subjectRepository.save(any(Subject.class)))
                .thenReturn(subjectTest);

        // Act
        var savedSubject = subjectService.addSubject(subjectDtoTest);

        // Assert
        assertEquals(savedSubject, subjectDtoTest);
        verify(subjectRepository, times(1)).save(any(Subject.class));
        verify(subjectRepository, times(1)).findById(anyString());
        verifyNoMoreInteractions(subjectRepository);
    }

    @Test
    public void shouldFailAddingAnSubjectWhenAlreadyExists() {
        // Arrange
        // It fails because already exists an Subject with that acronym
        when(subjectRepository.findById(anyString()))
                .thenReturn(Optional.ofNullable(subjectTest));

        // Assert && Act
        Assertions.assertThrows(ServerErrorException.class, () -> subjectService.addSubject(subjectDtoTest));
    }

    @Test
    public void updateSubjectByAcronym() throws Exception {
        // Arrange
        String newName = "newName";
        subjectDtoTest.setName(newName);

        when(subjectRepository.findById(anyString()))
                .thenReturn(Optional.ofNullable(subjectTest));

        when(subjectRepository.save(any(Subject.class)))
                .thenReturn(subjectMapper.subjectDtoToSubject(subjectDtoTest));

        // Act
        var updatedSubject = subjectService.updateSubjectByAcronym(subjectDtoTest.getAcronym(),subjectDtoTest);

        // Assert
        assertThat(updatedSubject).isEqualTo(subjectMapper.subjectToSubjectDto(subjectTest));
    }

    @Test
    public void shouldFailUpdatingASubjectThatDoesntExists() {
        // Arrange
        when(subjectRepository.findById(anyString()))
                .thenReturn(Optional.empty());

        // Assert & Act
        Assertions.assertThrows(ServerErrorException.class, () -> subjectService.updateSubjectByAcronym(anyString(), subjectDtoTest));
    }

    @Test
    public void deleteSubjectByAcronym() throws Exception {
        // Arrange
        doNothing()
                .when(subjectRepository)
                .deleteById(anyString());

        when(subjectRepository.findById(anyString()))
                .thenReturn(Optional.ofNullable(subjectTest));

        // Act
        var _boolean = subjectService.deleteSubjectByAcronym(anyString());

        // Assert
        assertTrue(_boolean);
    }

    @Test
    public void shouldFailAnSubjectThatDoesntExistsGivenHisAcronym() {
        // Arrange
        when(subjectRepository.findById(anyString()))
                .thenReturn(Optional.empty());

        // Act && Assert
        Assertions.assertThrows(ServerErrorException.class, ()-> subjectService.deleteSubjectByAcronym(anyString()));
    }
}