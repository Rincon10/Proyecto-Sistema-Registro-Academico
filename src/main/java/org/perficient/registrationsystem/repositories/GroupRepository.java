package org.perficient.registrationsystem.repositories;

import org.perficient.registrationsystem.model.Group;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface GroupRepository Created on 20/09/2022
 *
 * @Author Iván Camilo Rincon Saavedra
 */
@Repository
public interface GroupRepository extends CrudRepository<Group, Integer> {
}
