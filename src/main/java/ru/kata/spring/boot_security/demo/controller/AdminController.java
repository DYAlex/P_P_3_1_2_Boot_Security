package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping()
    public String adminPage(ModelMap model, Principal principal) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        model.addAttribute("principal", userService.loadUserByUsername(principal.getName()));
        model.addAttribute("newUser", new User());
        model.addAttribute("roles", roleService.getAllRoles());
        return "admin";
    }

    @PutMapping("/edit{id}")
    public String editUser(@ModelAttribute("user") User user) {
        userService.updateUser(user.getId(), user);
        return "redirect:/admin";
    }

    @PostMapping()
    public String addUserToDb(@ModelAttribute("newUser") @Valid User user, BindingResult bindingResult) {
        System.err.println("Called addUserToDb PostMapping");
        if (bindingResult.hasErrors()) {
            System.err.println("User not valid");
            System.err.println(bindingResult);
            return "redirect:/admin";
        }
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping(value = "/delete")
    public String deleteUser(@RequestParam long id) {
        userService.deleteById(id);
        return "redirect:/admin";
    }
}
