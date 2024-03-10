package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.RoleServiceImpl;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import javax.validation.Valid;
import java.util.Set;

@Controller
public class AdminController {
    private static final String REDIRECT_TO_ADMIN = "redirect:/admin";
    private final UserServiceImpl userService;
    private final RoleServiceImpl roleService;

    @Autowired
    public AdminController(UserServiceImpl userServiceImpl, RoleServiceImpl roleServiceImpl) {
        this.userService = userServiceImpl;
        this.roleService = roleServiceImpl;
    }

    @GetMapping("")
    public String showAdminPage(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "adminPage";
    }

    @GetMapping("/add_user")
    public String showFormForAddUser(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("roles", roleService.getAllRoles());
        return "addUser";
    }

    @PostMapping("/add_user")
    public String addUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, Set<Integer> roleIds, Model model) {
        Set<Role> roles = roleService.findDyIds(roleIds);
        user.setRoles(roles);
        userService.addUser(user);
        return REDIRECT_TO_ADMIN;
    }

    @GetMapping("/edit_user")
    public String formEditUser(@RequestParam(name = "id") Integer id, Model model) {
        model.addAttribute("user", userService.findUserById(id));
        return "editUser";
    }

    @PostMapping("/edit_user")
    public String submitEdit(@Valid @ModelAttribute("user") User user, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "editUser";
        } else {
            userService.editUser(user);
            return "redirect:/";
        }
    }

    @GetMapping("/delete_user")
    public String deleteUser(@RequestParam(name = "id") Integer id) {
        userService.removeUser(id);
        return REDIRECT_TO_ADMIN;
    }
}
