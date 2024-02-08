package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;

@Controller
public class RootController {
    private final UserService userService;
    public RootController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String indexPage(Model model, Principal principal) {
        if (principal != null) {
            model.addAttribute("user", userService.loadUserByUsername(principal.getName()));
        }
        return "index";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/logout")
    public String logoutPage() {
        return "login";
    }
}
