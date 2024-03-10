package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.entity.Role;

import java.util.Set;

public interface RoleDao {
    Role getRoleById(Integer id);

    Role getRoleByName(String name);

    Set<Role> getAllRoles();

    Role saveRole(Role role);

    void deleteRoleById(Integer id);

    Set<Role> findByIds(Set<Integer> ids);
}
