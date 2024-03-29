package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.entity.Role;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Repository
public class RoleDaoImpl implements RoleDao {
    private EntityManager entityManager;

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Set<Role> getAllRoles() {
        List<Role> roleList = entityManager.createQuery("SELECT r FROM Role r", Role.class).getResultList();
        return new LinkedHashSet<>(roleList);
    }

    @Override
    public Role saveRole(Role role) {
        entityManager.persist(role);
        return role;
    }

    @Override
    public Role getRoleById(Integer id) {
        return entityManager.find(Role.class, id);
    }


    @Override
    public Role getRoleByName(String name) {
        try {
            return entityManager.createQuery("SELECT r FROM Role r WHERE r.name = :name", Role.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Set<Role> findByIds(Set<Integer> id) {
        if (id == null || id.isEmpty()) {
            return new HashSet<>();
        }

        List<Role> rolesList = entityManager.createQuery("SELECT r FROM Role r WHERE r.id IN :id", Role.class)
                .setParameter("id", id)
                .getResultList();
        return new HashSet<>(rolesList);
    }

    @Override
    public void deleteRoleById(Integer id) {
        entityManager.createQuery("delete Role where id = :roleId")
                .setParameter("roleId", id)
                .executeUpdate();
    }


}
