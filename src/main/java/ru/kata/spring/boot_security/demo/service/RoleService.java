package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.entity.Role;

import java.util.Set;

public interface RoleService {
    Role getRoleById(Integer id);

    Set<Role> getAllRoles();

    Role saveRole(Role role);

    void deleteRoleById(Integer id);
    Set<Role> findDyIds(Set<Integer> ids);
}
