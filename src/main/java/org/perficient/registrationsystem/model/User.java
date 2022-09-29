package org.perficient.registrationsystem.model;

import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCrypt;

import javax.annotation.PostConstruct;
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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;

    private String createdAt = new Date().toString();

    @Column(nullable = false, unique = true)
    private String email;
    private String password;

    @PostConstruct
    private void hashPassword() {
        if (this.password != null) {
            this.password = BCrypt.hashpw(password, BCrypt.gensalt());
        }
    }

}
