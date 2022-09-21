package org.perficient.registrationsystem.model;

import lombok.Data;

import javax.persistence.*;

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
    protected String firstName;
    protected String lastName;

    protected String createdAt;
    protected String email;
    protected String password;

}
