package org.perficient.registrationsystem.model;

import lombok.Data;
import org.perficient.registrationsystem.dto.GroupDto;
import org.perficient.registrationsystem.mappers.ProfessorMapper;
import org.perficient.registrationsystem.mappers.SubjectMapper;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import java.sql.Time;

/**
 * Class Group created on 9/19/2022
 *
 * @Author Ivan Camilo Rincon Saavedra
 */

@Data
@Entity
@Table(name = "groups",
        uniqueConstraints = {@UniqueConstraint(name = "UniqueAcronymAndNumber", columnNames = {"subject_acronym", "number"})})
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
    @JoinColumn(name = "subject_acronym")
    private Subject subject;

    private Time startTime;
    private Time endTime;

    public void update(GroupDto groupDto) {
        this.setId(groupDto.getId());
        this.setNumber(groupDto.getNumber());
        this.setProfessor(ProfessorMapper.INSTANCE.professorDtoToProfessor(groupDto.getProfessor()));
        this.setSubject(SubjectMapper.INSTANCE.subjectDtoToSubject(groupDto.getSubject()));
        this.setStartTime(groupDto.getStartTime());
        this.setEndTime(groupDto.getEndTime());

    }
//    @ManyToMany
//    @JoinTable(name = "student_group", joinColumns = @JoinColumn(name = "group_id"),
//            inverseJoinColumns = @JoinColumn(name = "student_id"))
//    private Set<Student> students = new HashSet<>();

}
