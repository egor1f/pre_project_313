package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.RoleServiceImpl;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Set;
@Controller
@RequestMapping("/admin")
public class testController {

    private final String redirect = "redirect:/admin";
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public testController(UserServiceImpl userServiceImpl, RoleServiceImpl roleServiceImpl) {
        this.userService = userServiceImpl;
        this.roleService = roleServiceImpl;
    }

    @ModelAttribute("User")
    public User getUser() {
        return new User();
    }
    @GetMapping("")
    public String adminPage(Model model, Principal principal) {
        User User = userService.findUserByUsername(principal.getName());
        model.addAttribute("User", User);
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("roles", roleService.getAllRoles());
        return "/admin";
    }
    @GetMapping("/edit/{id}")
    public String showFormForEditUser(@PathVariable("id") int id, Model model) {
        model.addAttribute("editUser", userService.findUserById(id));
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("roles", roleService.getAllRoles());
        return "/admin";
    }
    @PatchMapping("/edit_user/{id}")
    public String editUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult,
                         @RequestParam("roles") Set<Integer> roleIds, @PathVariable("id") Integer id) {

        if (bindingResult.hasErrors()) {
            return "/admin";
        } else {
            Set<Role> roles = roleService.findDyIds(roleIds);
            user.setRoles(roles);
            user.setId(id);
            userService.editUser(user);
            return redirect;
        }
    }
    @GetMapping("/delete_user/{id}")
    public String deleteUser(@PathVariable("id") int id,
                             @ModelAttribute("deleteUser") User deleteUser,
                             Model model) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("deleteUser", userService.findUserById(id));
        return "/admin";
    }
    @GetMapping("/delete_user")
    public String deleteUser(@RequestParam(name = "id") Integer id) {
        userService.removeUser(id);
        return "redirect:/admin";
    }
    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id) {
        userService.removeUser(id);
        return redirect;
    }
    @GetMapping("/add_user")
    public String showFormForAddUser(@ModelAttribute("User") User User, Model model) {
        model.addAttribute("user", new User());
        return "/admin";
    }
    @PostMapping("/add_user")
    public String addUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, Model model,
                          @RequestParam("roles") Set<Integer> roleId) {
        if (bindingResult.hasErrors()) {
            return "/admin";
        } else {
            Set<Role> roles = roleService.findDyIds(roleId);
            user.setRoles(roles);
            userService.addUser(user);
            model.addAttribute("user", user);
            return redirect;
        }
    }
}