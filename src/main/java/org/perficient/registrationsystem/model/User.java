package org.perficient.registrationsystem.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Class USer created on 9/19/2022
 *
 * @Author Ivan Camilo Rincon Saavedra
 */

@Data
@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
public class User extends BaseEntity {
    private String firstName;
    private String lastName;

    private String createdAt = new Date().toString();

    @Column(nullable = false, unique = true)
    private String email;
    private String password;

}
