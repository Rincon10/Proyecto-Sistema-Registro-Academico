package org.perficient.registrationsystem.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.perficient.registrationsystem.dto.SubjectDto;
import org.perficient.registrationsystem.mappers.SubjectMapper;
import org.perficient.registrationsystem.repositories.SubjectRepository;
import org.perficient.registrationsystem.services.SubjectService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * Class SubjectServiceImpl Created on 20/09/2022
 *
 * @Author Iván Camilo Rincon Saavedra
 */
@Slf4j
@Service
public class SubjectServiceImpl implements SubjectService {
    private final SubjectRepository subjectRepository;

    private final SubjectMapper subjectMapper;

    public SubjectServiceImpl(SubjectRepository subjectRepository, SubjectMapper subjectMapper) {
        this.subjectRepository = subjectRepository;
        this.subjectMapper = subjectMapper;
    }

    @Override
    public Set<SubjectDto> getSubjects() {
        Set<SubjectDto> set = new HashSet<>();

        subjectRepository.findAll()
                .iterator()
                .forEachRemaining(s -> set.add(subjectMapper.subjectToSubjectDto(s)));
        return set;
    }

    @Override
    public SubjectDto addSubject(SubjectDto subjectDto) throws Exception {
        return null;
    }

    @Override
    public SubjectDto updateSubjectDto(Integer id, SubjectDto subjectDto) throws Exception {
        return null;
    }

    @Override
    public Boolean deleteSubjectById(Integer id) throws Exception {
        return null;
    }

    @Override
    public Boolean deleteSubjectByAcronym(String acronym) throws Exception {
        return null;
    }
}
