package org.perficient.registrationsystem.model;

import java.util.Set;

/**
 * Class Student created on 9/19/2022
 *
 * @Author Ivan Camilo Rincon Saavedra
 */
public class Student extends User {

    private Integer semester;
    private Set<Group> currentGroups;
}
