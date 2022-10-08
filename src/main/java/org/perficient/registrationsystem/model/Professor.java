package org.perficient.registrationsystem.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.perficient.registrationsystem.model.enums.Department;

import javax.persistence.*;
import java.util.Set;

/**
 * Class professor created on 9/19/2022
 *
 * @Author Ivan Camilo Rincon Saavedra
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "professors")
@PrimaryKeyJoinColumn(referencedColumnName = "id")
public class Professor extends User {
    @Enumerated(EnumType.STRING)
    private Department department;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "professor")
    private Set<Group> teachingIn;
}
