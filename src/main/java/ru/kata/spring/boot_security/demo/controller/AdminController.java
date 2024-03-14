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
import java.util.Objects;
import java.util.Set;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
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
    public String showFormForAddUser(Model model) {
        model.addAttribute("user", new User());
        return "addUser";
    }

    @PostMapping("/add_user")
    public String addUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, Model model,
                          @RequestParam("roles") Set<Integer> roleId) {
        if (bindingResult.hasErrors()) {
            return "addUser";
        } else {
            Set<Role> roles = roleService.findDyIds(roleId);
            user.setRoles(roles);
            userService.addUser(user);
            model.addAttribute("user", user);
            return "redirect:/admin";
        }
    }

    @GetMapping(value = "/edit_user")
    public String getUserEditForm(@RequestParam("id") Integer id, Model model) {
        User user = userService.findUserById(id);
        model.addAttribute("user", user);
        model.addAttribute("roles", roleService.getAllRoles());
        return "editUser";
    }

    @PostMapping(value = "/{id}/edit_user")
    public String updateUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult,
                             @RequestParam("roles") Set<Integer> roleIds, @PathVariable("id") Integer id) {
        if (bindingResult.hasErrors()) {
            return "editUser";
        } else {
            Set<Role> roles = roleService.findDyIds(roleIds);
            user.setRoles(roles);
            user.setId(id);
            userService.editUser(user);
            return "redirect:/admin";
        }
    }

    @GetMapping("/delete_user")
    public String deleteUser(@RequestParam(name = "id") Integer id) {
        userService.removeUser(id);
        return "redirect:/admin";
    }
}
