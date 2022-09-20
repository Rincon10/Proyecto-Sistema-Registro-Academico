package org.perficient.registrationsystem.model;

import java.util.HashSet;
import java.util.Set;

/**
 * Class Subject created on 9/19/2022
 *
 * @Author Ivan Camilo Rincon Saavedra
 */
public class Subject extends BaseEntity {

    private String acronym;
    private String name;
    private Set<Subject> prerequisites =  new HashSet<>();
}
