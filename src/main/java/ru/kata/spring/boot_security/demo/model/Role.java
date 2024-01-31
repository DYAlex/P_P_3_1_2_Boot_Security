package ru.kata.spring.boot_security.demo.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "role")
public class Role implements GrantedAuthority {
    @Id
    private Long id;

    private String name;

    public String getAuthority() {
        return getName();
    }
}
