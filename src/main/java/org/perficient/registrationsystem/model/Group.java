package org.perficient.registrationsystem.model;

import java.util.Set;

/**
 * Class Group created on 9/19/2022
 *
 * @Author Ivan Camilo Rincon Saavedra
 */
public class Group extends BaseEntity{

    private Set<Student> students;
    private Professor professor;
    private Subject subject;
}
