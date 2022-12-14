package org.perficient.registrationsystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.perficient.registrationsystem.model.enums.Department;

import javax.persistence.*;

/**
 * Class professor created on 9/19/2022
 *
 * @Author Ivan Camilo Rincon Saavedra
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder

@Entity
@Table(name = "professors")
@PrimaryKeyJoinColumn(referencedColumnName = "id")
public class Professor extends User {
    @Enumerated(EnumType.STRING)
    private Department department;

//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "professor")
//    private Set<Group> teachingIn;
}
