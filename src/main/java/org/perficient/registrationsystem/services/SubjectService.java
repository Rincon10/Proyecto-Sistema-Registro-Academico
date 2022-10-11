package org.perficient.registrationsystem.services;

import org.perficient.registrationsystem.dto.SubjectDto;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * Interface SubjectService Created on 20/09/2022
 *
 * @Author Iván Camilo Rincon Saavedra
 */
@Service
public interface SubjectService {

    //GET
    Set<SubjectDto> getSubjects() throws Exception;

    //ADD
    SubjectDto addSubject(SubjectDto subjectDto) throws Exception;

    //UPDATE

    SubjectDto updateSubjectDto(Integer id, SubjectDto subjectDto) throws Exception;

    //DELETE
    Boolean deleteSubjectById(Integer id) throws Exception;

    Boolean deleteSubjectByAcronym(String acronym) throws Exception;
}
