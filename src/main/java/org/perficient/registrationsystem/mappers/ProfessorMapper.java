package org.perficient.registrationsystem.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.perficient.registrationsystem.dto.ProfessorDto;
import org.perficient.registrationsystem.model.Professor;

/**
 * Interface ProfessorMapper Created on 20/09/2022
 *
 * @Author Iván Camilo Rincon Saavedra
 */
@Mapper
public interface ProfessorMapper {
    ProfessorMapper INSTANCE = Mappers.getMapper(ProfessorMapper.class);

    ProfessorDto professorToProfessorDto(Professor professor);

    Professor professorDtoToProfessor(ProfessorDto professorDto);
}
