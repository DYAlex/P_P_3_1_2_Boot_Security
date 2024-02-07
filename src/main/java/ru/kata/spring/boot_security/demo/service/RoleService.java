package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;

public interface RoleService {
    List<Role> getAllRoles();

    Role getRole(String rolename);

    Role getRoleById(Long roleId);

    Role findByName(String roleName);

    void addRole(Role role);
}
