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
    public String adminPage(ModelMap model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "admin";
    }

    @GetMapping("/show")
    public String show(@RequestParam("id") long id, Model model) {
        model.addAttribute("user", userService.findById(id));
        return "/admin/show";
    }

    @GetMapping("/edit")
    public String editUserPage(@RequestParam("id") long id, Model model) {
        model.addAttribute("user", userService.findById(id));
        model.addAttribute("roles", roleService.getAllRoles());
        return "/admin/edit";
    }

    @PutMapping("/edit{id}")
    public String editUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/edit";
        }
        userService.updateUser(user.getId(), user);
        return "redirect:/admin";
    }

    @GetMapping("/new")
    public String createUserForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.getAllRoles());
        return "/admin/new";
    }

    @PostMapping()
    public String addUserToDb(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/new";
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
