package org.perficient.registrationsystem.services.impl;

import org.perficient.registrationsystem.dto.GroupDto;
import org.perficient.registrationsystem.mappers.GroupMapper;
import org.perficient.registrationsystem.model.Group;
import org.perficient.registrationsystem.repositories.GroupRepository;
import org.perficient.registrationsystem.services.GroupService;
import org.perficient.registrationsystem.services.exceptions.ServerErrorException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Class GroupServiceImpl Created on 29/09/2022
 *
 * @Author Iván Camilo Rincon Saavedra
 */
@Service
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;

    private final GroupMapper groupMapper;

    public GroupServiceImpl(GroupRepository repository, GroupMapper mapper) {
        this.groupRepository = repository;
        this.groupMapper = mapper;
    }

    private Group findById(Integer id) throws Exception {
        Optional<Group> group = groupRepository.findById(id);
        group.orElseThrow(() -> new ServerErrorException(ServerErrorException.DOESNT_EXITS, HttpStatus.NOT_FOUND.value()));

        group.get().getProfessor().setPassword(null);
        return group.get();

    }

    @Override
    public Set<GroupDto> getAllGroups() {
        Set<GroupDto> set = new HashSet<>();

        groupRepository.findAll().iterator().forEachRemaining(group -> {
            group.getProfessor().setPassword(null);
            set.add(groupMapper.groupToGroupDto(group));
        });
        return set;
    }

    @Override
    public GroupDto findGroupById(Integer id) throws Exception {
        Group group = findById(id);

        return groupMapper.groupToGroupDto(group);
    }

    @Override
    public Boolean addGroup(GroupDto groupDto) throws Exception {
        Group group = groupMapper.groupDtoToGroup(groupDto);
        try {
            groupRepository.save(group);
            return true;
        } catch (Exception e) {
            throw new ServerErrorException(ServerErrorException.ALREADY_EXITS, HttpStatus.FORBIDDEN.value());
        }
    }

    @Override
    @Transactional
    public GroupDto updateGroupById(Integer id, GroupDto groupDto) throws Exception {
        //Checking if the user exists
        Group group = findById(id);
        //Updating
        group.update(groupDto);
        groupRepository.save(group);
        return groupDto;
    }

    @Transactional
    @Override
    public Boolean deleteGroupById(Integer id) throws Exception {
        //Checking if the user exists
        findById(id);
        groupRepository.deleteById(id);
        return true;
    }
}
