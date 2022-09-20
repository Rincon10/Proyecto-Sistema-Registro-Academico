package org.perficient.registrationsystem.model;

import org.perficient.registrationsystem.model.enums.Department;

import java.util.Set;

/**
 * Class professor created on 9/19/2022
 *
 * @Author Ivan Camilo Rincon Saavedra
 */
public class Professor extends User {

    private Department department;
    private Set<Group> teachingIn;
}
