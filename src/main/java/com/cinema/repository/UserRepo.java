package com.cinema.repository;

import com.cinema.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

/**
 * User repository is responsible for manipulation with users table in the database
 */
public interface UserRepo extends CrudRepository<User, Long> {

    /**
     * Find all users
     * @return list of users
     * @see User
     */
    List<User> findAll();

    /**
     * Find the user by login
     * @param login username
     * @return Optional of users
     * @see User
     */
    Optional<User> findByLogin(String login);

    /**
     * Find the user by email
     * @param email mail
     * @return Optional of desired user
     * @see User
     */
    Optional<User> findByEmail(String email);

    /**
     * Find all users who match by the active toggle
     * @param isActive toggle
     * @return list of users
     * @see User
     */
    List<User> findByActiveEquals(Boolean isActive);
}
