package org.perficient.registrationsystem.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.perficient.registrationsystem.dto.SubjectDto;
import org.perficient.registrationsystem.mappers.SubjectMapper;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Class Subject created on 9/19/2022
 *
 * @Author Ivan Camilo Rincon Saavedra
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "subjects")
public class Subject extends BaseEntity {
    @Id
    private String acronym;
    private String name;

//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "subject")
//    private Set<Group> groups = new HashSet<>();

    @ManyToMany(cascade = CascadeType.DETACH)
    @JoinTable(name = "prerequisites", joinColumns = @JoinColumn(name = "subject1_id"),
            inverseJoinColumns = @JoinColumn(name = "subject2_id"))
    private Set<Subject> prerequisites = new HashSet<>();

    public void update(SubjectDto subjectDto) {
        this.setAcronym(subjectDto.getAcronym());
        this.setName(subjectDto.getName());
        this.setPrerequisites(SubjectMapper.INSTANCE.subjectDtoSetToSubjectSet(subjectDto.getPrerequisites()));
    }
}
