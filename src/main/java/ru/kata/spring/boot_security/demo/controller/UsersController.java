package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UsersController {
    private final UserService userService;
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String userPage(Model model, Principal principal) {
        model.addAttribute("user", userService.loadUserByUsername(principal.getName()));
        return "user";
    }
}
