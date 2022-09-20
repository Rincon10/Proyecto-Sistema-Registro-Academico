package org.perficient.registrationsystem.repositories;

import org.perficient.registrationsystem.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Interface SubjectRepository Created on 20/09/2022
 *
 * @Author Iván Camilo Rincon Saavedra
 */
@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
}
