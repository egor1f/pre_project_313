package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.entity.User;

import java.util.List;

public interface UserService {
    void addUser(User user);

    List<User> getAllUsers();

    void removeUser(Integer userId);

    User findUserById(Integer userId);

    void editUser(User user);

    User findUserByUsername(String username);
}