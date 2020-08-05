package com.cinema.repository;

import com.cinema.entity.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * Role repository is responsible for manipulation with roles table in the database
 */
public interface RoleRepo extends CrudRepository<Role, Long> {

    /**
     * Find the role
     * @param role desired role
     * @return Optional of desired role
     */
    Optional<Role> findByRole(String role);

}
