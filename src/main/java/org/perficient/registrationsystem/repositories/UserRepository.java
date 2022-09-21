package org.perficient.registrationsystem.repositories;

import org.perficient.registrationsystem.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface UserRepository Created on 20/09/2022
 *
 * @Author Iván Camilo Rincon Saavedra
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {
}
