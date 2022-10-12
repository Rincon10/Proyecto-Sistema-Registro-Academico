package org.perficient.registrationsystem.repositories;

import org.perficient.registrationsystem.model.Student;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface StudentRepository Created on 20/09/2022
 *
 * @Author Iván Camilo Rincon Saavedra
 */
@Repository
public interface StudentRepository extends CrudRepository<Student, Long> {
}
