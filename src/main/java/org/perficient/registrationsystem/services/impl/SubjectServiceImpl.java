package org.perficient.registrationsystem.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.perficient.registrationsystem.model.Subject;
import org.perficient.registrationsystem.repositories.SubjectRepository;
import org.perficient.registrationsystem.services.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
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

    public SubjectServiceImpl(@Autowired SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    @Override
    public Set<Subject> getSubjects() {
        Set<Subject> subjects = new HashSet<>();
        subjectRepository.findAll().iterator().forEachRemaining(subjects::add);

        return subjects;
    }
}
