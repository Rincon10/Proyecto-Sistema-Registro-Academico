package org.perficient.registrationsystem.model;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Class Subject created on 9/19/2022
 *
 * @Author Ivan Camilo Rincon Saavedra
 */
@Data
@Entity
@Table(name = "subjects")
public class Subject extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String acronym;
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "subject")
    private Set<Group> groups = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "prerequisites", joinColumns = @JoinColumn(name = "subject1_id"),
            inverseJoinColumns = @JoinColumn(name = "subject2_id"))
    private Set<Subject> prerequisites = new HashSet<>();
}
