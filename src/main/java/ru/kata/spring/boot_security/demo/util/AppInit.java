package ru.kata.spring.boot_security.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
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
        roleService.addRole(new Role(roleAdmin));
        roleService.addRole(new Role(roleUser));

        Set<Role> adminRole = new HashSet<>();
        Set<Role> userRole = new HashSet<>();
        Set<Role> allRoles = new HashSet<>();

        adminRole.add(roleService.findByName(roleAdmin));
        userRole.add(roleService.findByName(roleUser));
        allRoles.add(roleService.findByName(roleAdmin));
        allRoles.add(roleService.findByName(roleUser));

        userService.saveUser(new User("Admin", "Adminov", "admin@admin.ru", "admin@admin.ru", "admin", adminRole));
        userService.saveUser(new User("User", "Userov", "user@user.ru", "user@user.ru", "user", userRole));
        userService.saveUser(new User("All", "Allin", "all@all.ru", "all@all.ru", "all", allRoles));
    }
}
