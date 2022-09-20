package org.perficient.registrationsystem.model;

import lombok.Data;

/**
 * Class USer created on 9/19/2022
 *
 * @Author Ivan Camilo Rincon Saavedra
 */
@Data
public class User extends BaseEntity {

    private String firstName;
    private String lastName;

    private String createdAt;
    private String email;
    private String password;
}
