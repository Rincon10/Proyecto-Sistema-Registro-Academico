package org.perficient.registrationsystem.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.perficient.registrationsystem.dto.SubjectDto;
import org.perficient.registrationsystem.model.Subject;

/**
 * Interface SubjectMapper Created on 20/09/2022
 *
 * @Author Iván Camilo Rincon Saavedra
 */
@Mapper
public interface SubjectMapper {
    SubjectMapper INSTANCE = Mappers.getMapper(SubjectMapper.class);

    SubjectDto subjectToSubjectDto(Subject subject);

    Subject subjectDtoToSubject(SubjectDto subjectDto);
}
