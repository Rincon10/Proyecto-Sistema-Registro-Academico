package org.perficient.registrationsystem.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.util.Set;

/**
 * Class Student created on 9/19/2022
 *
 * @Author Ivan Camilo Rincon Saavedra
 */

@Data
@Entity
@Table(name = "students")
@PrimaryKeyJoinColumn(referencedColumnName = "id")
public class Student extends User {
    private Integer semester;

    @ManyToMany(mappedBy = "students")
    private Set<Group> currentGroups;
}
