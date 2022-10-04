package org.perficient.registrationsystem.repositories;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.perficient.registrationsystem.model.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Time;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
public class GroupRepositoryTest {

    @Autowired
    GroupRepository repository;

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void shouldNotFindGroupsIfRepositoryIsEmpty() {
        // given
        Iterable groups = repository.findAll();

        // then
        assertThat(groups).isEmpty();
    }

    @Test
    public void shouldAddAGroup() {
        // given
        Group group = new Group();
        group.setId(1);
        group.setNumber(1);

        // when
        Group savedGroup = repository.save(group);

        // then
        assertThat(savedGroup).hasFieldOrPropertyWithValue("id", 1);
        assertThat(savedGroup).hasFieldOrPropertyWithValue("number", 1);
    }

    @Test
    public void shouldFindAllGroups() {
        // given
        int times = 2;

        Group group1 = new Group();
        group1.setId(1);
        group1.setNumber(1);
        repository.save(group1);

        Group group2 = new Group();
        group2.setId(2);
        group2.setNumber(2);
        repository.save(group2);

        // when
        Iterable groups = repository.findAll();

        // then
        assertThat(groups).hasSize(times).contains(group1, group2);
    }

    @Test
    public void findGroupByIdWhenExists() {
        // given
        Integer id = 1;
        Group group = new Group();
        group.setId(id);
        repository.save(group);

        // when
        Group optional = repository.findById(id).get();

        // then
        assertEquals(id, optional.getId());
        assertThat(group).isEqualTo(optional);
    }

    @Test
    public void emptyGroupWhenIdDoesntExists() {
        // given
        Integer id = 100;

        // when
        Optional<Group> optional = repository.findById(id);

        // then
        assertTrue(optional.isEmpty());
    }

    //UPDATING
    @Test
    public void shouldUpdateGroupById() {
        // given
        Integer id = 1;
        Time sqlTime1 = Time.valueOf("18:45:20");
        Time sqlTime2 = Time.valueOf("10:00:20");

        Group group = new Group();
        group.setStartTime(sqlTime1);

        assertThat(group.getStartTime()).isEqualTo(sqlTime1);
        repository.save(group);

        // when
        Group savedGroup = repository.findById(1).get();

        savedGroup.setStartTime(sqlTime2);

        // then
        Group savedGroup2 = repository.findById(1).get();

        assertThat(savedGroup2.getStartTime()).isEqualTo(savedGroup.getStartTime());
    }

    @Test
    public void shouldDeleteAGroupById() {
        // given
        Integer id = 1;

        Group group = new Group();

        repository.save(group);

        // when
        repository.deleteById(id);
        Iterable groups = repository.findAll();

        // then

        assertThat(groups).isEmpty();
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void shouldFailDeletingAGroupByIdForNoGruop() {
        // given
        Integer id = 1;

        Group group = new Group();

        // when
        repository.deleteById(id);
    }
}
