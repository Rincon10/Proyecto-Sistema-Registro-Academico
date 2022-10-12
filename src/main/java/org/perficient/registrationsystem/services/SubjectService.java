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
    Set<SubjectDto> getAllSubjects() throws Exception;

    SubjectDto getSubjectByAcronym( String acronym) throws Exception;

    //ADD
    SubjectDto addSubject(SubjectDto subjectDto) throws Exception;

    //UPDATE

    SubjectDto updateSubjectByAcronym(String acronym, SubjectDto subjectDto) throws Exception;

    //DELETE

    Boolean deleteSubjectByAcronym(String acronym) throws Exception;
}
