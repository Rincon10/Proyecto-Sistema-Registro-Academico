package org.perficient.registrationsystem.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import java.util.HashSet;
import java.util.Set;

/**
 * Class Group created on 9/19/2022
 *
 * @Author Ivan Camilo Rincon Saavedra
 */

@Data
@Entity
@Table(name = "groups")
public class Group extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Positive
    private Integer number;

    @ManyToOne
    @JoinColumn(name = "professor_id")
    private Professor professor;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @ManyToMany
    @JoinTable(name = "student_group", joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id"))
    private Set<Student> students = new HashSet<>();

}
