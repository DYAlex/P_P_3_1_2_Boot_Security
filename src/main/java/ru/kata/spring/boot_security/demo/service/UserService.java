package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.model.User;
import java.util.List;

public interface UserService extends UserDetailsService {
    User findById(Long id);
//    User findByUsername(String username);
    List<User> findAll();
    void saveUser(User user);
    void updateUser(Long id, User updatedUser);
    void deleteById(Long id);
    User loadUserByUsername(String username);
}
