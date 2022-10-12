package org.perficient.registrationsystem.repositories;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.runner.RunWith;
import org.perficient.registrationsystem.model.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@DataJpaTest
public class SubjectRepositoryTest {

    @Autowired
    SubjectRepository subjectRepository;

    private Subject testSubject;

    @Before
    public void setUp() {
        testSubject = Subject
                .builder()
                .acronym("TEST")
                .name("Subject Test")
                .prerequisites(new HashSet<>())
                .build();
    }

    @Test
    public void should_not_find_subjects_if_repository_is_empty() {
        // Arrange & Act
        Iterable<Subject> subjects = subjectRepository.findAll();

        // Assert
        assertThat(subjects).isEmpty();
    }

    @Test
    public void should_add_a_subject() {
        // Arrange
        int expectedSize = 1;

        // Act
        var savedSubject = subjectRepository.save(testSubject);

        Iterable<Subject> subjects = subjectRepository.findAll();

        // Assert
        assertThat(subjects).hasSize(expectedSize);
        assertThat(testSubject)
                .usingRecursiveComparison()
                .isEqualTo(savedSubject);
    }

    @Test
    public void should_find_all_groups_with_subjects() {
        // Arrange
        int times = 2;
        Set<Subject> subjectSet = new HashSet<>();

        for (int i = 0; i < times; i++) {
            testSubject = Subject
                    .builder()
                    .acronym("TEST" + i)
                    .name("Subject Test" + i)
                    .prerequisites(new HashSet<>())
                    .build();
            subjectSet.add(testSubject);
        }

        // Act
        var savedSubjects = subjectRepository.saveAll(subjectSet);

        // Assert
        assertThat(savedSubjects).hasSize(times);
        assertThat(savedSubjects)
                .usingRecursiveComparison()
                .isEqualTo(subjectSet);
    }

    @Test
    public void find_subject_when_acronym_exists() throws Exception {
        // Arrange
        String acronym = testSubject.getAcronym();
        subjectRepository.save(testSubject);

        // Act
        var subjectSaved = subjectRepository.findById(acronym)
                .orElseThrow(() -> new Exception("Should show error"));

        // Assert
        assertEquals(testSubject, subjectSaved);
    }

    @Test
    public void should_return_empty_optional_when_acronym_doesnt_exists() {
        // Arrange
        String acronym = testSubject.getAcronym();

        // Act
        var optionalSubject = subjectRepository.findById(acronym);

        // Assert
        assertThat(optionalSubject).isEmpty();
    }

    @Test
    public void should_update_subject_by_acronym() {
        // Arrange
        String oldName = testSubject.getName();
        String newName = "New Test Name";
        String acronym = testSubject.getAcronym();
        String oldSubject = testSubject.toString();

        // Act
        var savedSubject = subjectRepository.save(testSubject);
        savedSubject.setName(newName);

        subjectRepository.save(savedSubject);
        var updatedSubject = subjectRepository.findById(acronym)
                .orElseThrow();

        // Assert
        assertThat(oldSubject).isNotEqualTo(updatedSubject.toString());
        assertThat(oldName).isNotEqualTo(updatedSubject.getName());
    }

    @Test
    public void should_delete_a_group_given_a_valid_acronym() {
        // Arrange
        int size = 1;
        String acronym = testSubject.getAcronym();

        // Act
        subjectRepository.save(testSubject);
        Iterable<Subject> subjects = subjectRepository.findAll();

        subjectRepository.deleteById(acronym);
        Iterable<Subject> lastSubjects = subjectRepository.findAll();

        // Assert
        assertThat(lastSubjects).isEmpty();
        assertThat(subjects).hasSize(size);
    }
    
    @Test(expected = EmptyResultDataAccessException.class)
    public void should_fail_deleting_a_subject_for_an_acronym_that_doesnt_exists() {
        // Arrange
        String acronym = "FAKE";

        // Act
        subjectRepository
                .deleteById(acronym);
        
        // Assert
        // We expect an exception at this point
    }
}