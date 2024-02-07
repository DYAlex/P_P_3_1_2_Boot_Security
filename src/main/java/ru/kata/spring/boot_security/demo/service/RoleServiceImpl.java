package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService{
    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public Role getRole(String roleName) {
        return roleRepository.findByName(roleName);
    }

    public Role getRoleById(Long roleId) {
        return roleRepository.getById(roleId);
    }

    public Role findByName(String roleName) {
        return roleRepository.findByName(roleName);
    }

    public void addRole(Role role) {
        roleRepository.saveAndFlush(role);
    }
}
