package ru.kata.spring.boot_security.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Transactional
    public void updateUser(Long id, User updatedUser) {
        User user = findById(id);

        user.setName(updatedUser.getName());
        user.setLastName(updatedUser.getLastName());
        user.setEmail(updatedUser.getEmail());
        user.setUsername(updatedUser.getUsername());
        user.setRoles(updatedUser.getRoles());
        user.setEnabled(updatedUser.isEnabled());
        user.setAccountNonExpired(updatedUser.isAccountNonExpired());
        user.setAccountNonLocked(updatedUser.isAccountNonLocked());
        user.setCredentialsNonExpired(updatedUser.isCredentialsNonExpired());

        saveUser(user);
    }


    @Transactional
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User loadedUser = userRepository.findByUsername(username);
        if (loadedUser == null) {
            throw new UsernameNotFoundException("Unknown user: " + username);
        }
        return loadedUser;
    }
}
