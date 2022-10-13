package org.perficient.registrationsystem.repositories;

import org.perficient.registrationsystem.model.Professor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface ProfessorRepository Created on 20/09/2022
 *
 * @Author Iván Camilo Rincon Saavedra
 */
@Repository
public interface ProfessorRepository extends CrudRepository<Professor, Long> {
}
