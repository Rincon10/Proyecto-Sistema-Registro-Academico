package org.perficient.registrationsystem.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * Class Student created on 9/19/2022
 *
 * @Author Ivan Camilo Rincon Saavedra
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "students")
@PrimaryKeyJoinColumn(referencedColumnName = "id")
public class Student extends User {
    private Integer semester;

//    @ManyToMany(mappedBy = "students")
//    private Set<Group> currentGroups;
}
