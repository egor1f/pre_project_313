package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.entity.User;

import java.util.List;


public interface UserDao {
    void addUser(User user);

    List<User> getAllUsers();

    void removeUser(Integer id);

    User findUserById(Integer id);

    void editUser(User user);
    User findByUsername(String username);
}
