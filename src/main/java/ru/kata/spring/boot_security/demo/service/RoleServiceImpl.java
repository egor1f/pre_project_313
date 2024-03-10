package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.entity.Role;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class RoleServiceImpl implements RoleService {
    private final RoleDao roleRepository;

    @Autowired
    public RoleServiceImpl(RoleDao roleRepository) {
        this.roleRepository = roleRepository;
    }


    @Override
    public Role getRoleById(Integer id) {
        return roleRepository.getRoleById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Set<Role> getAllRoles() {
        return roleRepository.getAllRoles();
    }

    @Override
    public Role saveRole(Role role) {
        return roleRepository.saveRole(role);
    }

    @Override
    public void deleteRoleById(Integer id) {
        roleRepository.deleteRoleById(id);
    }

    @Override
    public Set<Role> findDyIds(Set<Integer> ids) {
        return roleRepository.findByIds(ids);
    }
}