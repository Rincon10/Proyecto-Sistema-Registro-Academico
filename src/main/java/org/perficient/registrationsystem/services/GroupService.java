package org.perficient.registrationsystem.services;

import org.perficient.registrationsystem.dto.GroupDto;

import java.sql.SQLException;
import java.util.Set;

/**
 * Interface GroupService Created on 29/09/2022
 *
 * @Author Iván Camilo Rincon Saavedra
 */
public interface GroupService {

    //GET
    Set<GroupDto> getAllGroups() throws SQLException;

    GroupDto findGroupById(Integer id) throws SQLException;

    //ADD
    Boolean addGroup(GroupDto groupDto) throws SQLException;

    //UPDATE
    GroupDto updateGroupById(Integer id, GroupDto groupDto) throws SQLException;

    //DELETE
    Boolean deleteGroupById(Integer id) throws SQLException;
}
