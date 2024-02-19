package ru.kata.spring.boot_security.demo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kata.spring.boot_security.demo.model.Role;

import javax.validation.constraints.*;
import java.util.Set;

@Data
@NoArgsConstructor
public class UserDto {
    private Long id;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 3, max = 64, message = "Name should be between 3 and 64 characters long")
    private String name;

    @NotEmpty(message = "Last name should not be empty")
    @Size(min = 2, max = 64, message = "Last name should be between 2 and 64 characters long")
    private String lastName;

    @Min(value = 1, message = "Age should be greater than 0")
    @Max(value = 127, message = "Age should be less than 128")
    private int age;

    @NotEmpty
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z]{2,4}$", message = "Email should be in format: name@subdomain.domain")
    private String username;

    @NotEmpty
    @Size(min = 4, max = 64, message = "Password should be between 4 and 64 characters long")
    private String password;

    @NotEmpty
    private Set<Role> roles;
}
