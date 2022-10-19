package org.perficient.registrationsystem.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.perficient.registrationsystem.dto.SubjectDto;
import org.perficient.registrationsystem.mappers.SubjectMapper;
import org.perficient.registrationsystem.model.Subject;
import org.perficient.registrationsystem.repositories.SubjectRepository;
import org.perficient.registrationsystem.services.SubjectService;
import org.perficient.registrationsystem.services.exceptions.ServerErrorException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    private Subject findSubjectByAcronym(String acronym) throws Exception {
        var subject = subjectRepository.findById(acronym)
                .orElseThrow(() -> new ServerErrorException("The Subject that you are looking for doesn't exists.", HttpStatus.NOT_FOUND.value()));
        subject.getPrerequisites()
                .forEach(s -> s.setPrerequisites(null));

        return subject;
    }

    @Override
    public Set<SubjectDto> getAllSubjects() {
        Set<SubjectDto> set = new HashSet<>();

        subjectRepository.findAll()
                .iterator()
                .forEachRemaining(s -> {
                    s.getPrerequisites().forEach(p -> p.setPrerequisites(null));
                    set.add(subjectMapper.subjectToSubjectDto(s));
                });
        return set;
    }

    @Override
    public SubjectDto getSubjectByAcronym(String acronym) throws Exception {
        return subjectMapper.subjectToSubjectDto(findSubjectByAcronym(acronym));
    }

    @Override
    public SubjectDto addSubject(SubjectDto subjectDto) throws Exception {
        if (subjectRepository.findById(subjectDto.getAcronym()).isPresent()) {
            throw new ServerErrorException("Subject already exits!", HttpStatus.FORBIDDEN.value());
        }
        try {
            Subject subject = subjectMapper.subjectDtoToSubject(subjectDto);
            return subjectMapper.subjectToSubjectDto(subjectRepository.save(subject));
        } catch (DataIntegrityViolationException e) {
            throw new ServerErrorException("Subject already exits!", HttpStatus.FORBIDDEN.value());
        } catch (Exception e) {
            throw new ServerErrorException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @Transactional
    @Override
    public SubjectDto updateSubjectByAcronym(String acronym, SubjectDto subjectDto) throws Exception {
        //we verify that the subject exists
        Subject subject = findSubjectByAcronym(acronym);
        try {
            subject.update(subjectDto);
            return subjectMapper
                    .subjectToSubjectDto(subjectRepository.save(subject));
        } catch (Exception e) {
            throw new ServerErrorException("We couldn't Updated the subject " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @Transactional
    @Override
    public Boolean deleteSubjectByAcronym(String acronym) throws Exception {
        //we verify that the subject exists
        findSubjectByAcronym(acronym);
        subjectRepository.deleteById(acronym);
        return true;
    }
}
