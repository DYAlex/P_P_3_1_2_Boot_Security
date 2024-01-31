package ru.kata.spring.boot_security.demo.model;

import lombok.Data;
import javax.persistence.*;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "role")
    private String role;

    public User() {
    }

    public User(String name, String lastName, String role) {
        this.name = name;
        this.lastName = lastName;
        this.role = role;
    }
}
