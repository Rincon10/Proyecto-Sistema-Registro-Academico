package org.perficient.registrationsystem.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.perficient.registrationsystem.dto.GroupDto;
import org.perficient.registrationsystem.model.Group;


/**
 * Interface GroupMapper Created on 20/09/2022
 *
 * @Author Iván Camilo Rincon Saavedra
 */
@Mapper
public interface GroupMapper {
    GroupMapper INSTANCE = Mappers.getMapper(GroupMapper.class);

    GroupDto groupToGroupDto(Group group);

    Group groupDtoToGroup(GroupDto groupDto);
}
