package org.perficient.registrationsystem.services;

import org.perficient.registrationsystem.model.Subject;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * Interface SubjectService Created on 20/09/2022
 *
 * @Author Iván Camilo Rincon Saavedra
 */
@Service
public interface SubjectService {

    Set<Subject> getSubjects();
}
