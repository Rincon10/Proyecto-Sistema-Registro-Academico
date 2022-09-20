package org.perficient.registrationsystem.model;

import lombok.Data;

import java.io.Serializable;

/**
 * Class BaseEntity created on 9/19/2022
 *
 * @Author Ivan Camilo Rincon Saavedra
 */
@Data
public class BaseEntity implements Serializable {
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
