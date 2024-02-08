package ru.kata.spring.boot_security.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Component
public class AppInit {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AppInit(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostConstruct
    public void initializedDataBase() {
        String roleAdmin = "ROLE_ADMIN";
        String roleUser = "ROLE_USER";
        Set<Role> adminRole = new HashSet<>();
        Set<Role> userRole = new HashSet<>();
        Set<Role> allRoles = new HashSet<>();
        // the hashed password was calculated using the following code
        // the hash should be done up front, so malicious users cannot discover the
        // password
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String adminPassword = encoder.encode("admin");
        String userPassword = encoder.encode("user");
        String allPassword = encoder.encode("all");

        roleService.addRole(new Role(roleAdmin));
        roleService.addRole(new Role(roleUser));

        adminRole.add(roleService.findByName(roleAdmin));
        userRole.add(roleService.findByName(roleUser));
        allRoles.add(roleService.findByName(roleAdmin));
        allRoles.add(roleService.findByName(roleUser));

        userService.saveUser(new User("Admin", "Adminov", (byte) 21, "admin@admin.ru", "admin@admin.ru", adminPassword, adminRole));
        userService.saveUser(new User("User", "Userov", (byte) 45, "user@user.ru", "user@user.ru", userPassword, userRole));
        userService.saveUser(new User("All", "Allin", (byte) 33, "all@all.ru", "all@all.ru", allPassword, allRoles));
    }
}
