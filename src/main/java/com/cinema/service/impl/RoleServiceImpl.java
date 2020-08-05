package com.cinema.service.impl;

import com.cinema.entity.Role;
import com.cinema.repository.RoleRepo;
import com.cinema.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Role service implementation <br>
 * Fulfil variety operation with roles
 * @see RoleService
 */
@Service
public class RoleServiceImpl implements RoleService {

    /**
     * RoleRepo field is for performing search, save and update the role entity operation in database
     * @see RoleRepo
     */
    private final RoleRepo roleRepo;

    /**
     * Current class constructor
     * @param roleRepo <b>RoleRepo</b> interface
     */
    @Autowired
    public RoleServiceImpl(RoleRepo roleRepo) {
        this.roleRepo = roleRepo;
    }

    /**
     * Save a new role
     * @param role a new role to add
     */
    void save(Role role) {
        roleRepo.save(role);
    }

    /**
     * Find role entity by role name
     * @param role desired user role
     * @return Optional of role entity
     * @see Role
     */
    Optional<Role> findByRole(String role) {
        return roleRepo.findByRole(role);
    }

}
