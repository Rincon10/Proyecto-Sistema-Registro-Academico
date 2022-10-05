package org.perficient.registrationsystem.services;

import org.perficient.registrationsystem.dto.GroupDto;

import java.util.Set;

/**
 * Interface GroupService Created on 29/09/2022
 *
 * @Author Iván Camilo Rincon Saavedra
 */
public interface GroupService {

    //GET
    Set<GroupDto> getAllGroups() throws Exception;

    GroupDto findGroupById(Integer id) throws Exception;

    //ADD
    Boolean addGroup(GroupDto groupDto) throws Exception;

    //UPDATE
    GroupDto updateGroupById(Integer id, GroupDto groupDto) throws Exception;

    //DELETE
    Boolean deleteGroupById(Integer id) throws Exception;
}
