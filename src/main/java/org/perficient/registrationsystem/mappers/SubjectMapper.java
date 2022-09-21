package org.perficient.registrationsystem.mappers;

import org.mapstruct.Mapper;
import org.perficient.registrationsystem.dto.SubjectDto;
import org.perficient.registrationsystem.model.Subject;

/**
 * Interface SubjectMapper Created on 20/09/2022
 *
 * @Author Iván Camilo Rincon Saavedra
 */
@Mapper
public interface SubjectMapper {

    SubjectDto subjectToSubjectDto(Subject subject);

    Subject subjectDtoToSubject(SubjectDto subjectDto);
}
