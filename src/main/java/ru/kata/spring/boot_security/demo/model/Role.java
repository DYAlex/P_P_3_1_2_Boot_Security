package ru.kata.spring.boot_security.demo.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@Entity
@Table(name = "role")
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Size(min = 4, max = 32, message = "Role name should be between 4 and 32 characters long")
    private String name;

    public Role(String name) {
        this.name = name;
    }

    public String getAuthority() {
        return getName();
    }
}
