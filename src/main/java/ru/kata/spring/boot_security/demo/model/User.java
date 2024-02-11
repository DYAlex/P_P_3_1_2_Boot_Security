package ru.kata.spring.boot_security.demo.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Collection;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Size(min = 3, max = 64, message = "Name should be between 3 to 64 characters long")
    @Column(name = "name")
    private String name;

    @NotEmpty
    @Size(min = 3, max = 64, message = "Last name should be between 3 to 64 characters long")
    @Column(name = "last_name")
    private String lastName;

    @NotEmpty
    @Email
    private String email;

    @Min(value = 1, message = "Age should be greater than 0")
    @Max(value = 127, message = "Age should be less than 128")
    private int age;

    @NotEmpty
    @Email
    @Column(unique = true)
    private String username;

    @NotEmpty
    @Size(min = 3, max = 64, message = "Password should be between 3 to 64 characters long")
    private String password;

    @NotEmpty
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    @Fetch(FetchMode.JOIN)
    private Set<Role> roles;

    private boolean accountNonExpired = true;

    private boolean accountNonLocked = true;

    private boolean credentialsNonExpired = true;
    private boolean enabled = true;

    public User(String name, String lastName, String email, int age, String username, String password, Set<Role> roles) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.age = age;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public User(String name, String lastName, String email, int age, String username, String password, boolean accountNonExpired, boolean accountNonLocked, boolean credentialsNonExpired, boolean enabled, Set<Role> roles) {
        this(name, lastName, email, age, username, password, roles);
        this.accountNonExpired = accountNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
        this.enabled = enabled;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }
}
