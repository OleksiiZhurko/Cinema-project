package com.cinema.service;

import com.cinema.dto.UserDto;
import com.cinema.exceptions.RoleNotFoundException;
import com.cinema.exceptions.UserEmailAlreadyExistsException;
import com.cinema.exceptions.UserLoginAlreadyExistsException;
import com.cinema.exceptions.UserNotFoundException;

import java.util.List;
import java.util.Optional;

/**
 * User service interface
 */
public interface UserService {

    /**
     * To find all users \
     * @return list of users
     */
    List<UserDto> findAll();

    /**
     * To find one by login
     * @param login username
     * @return Optional of user dto
     */
    Optional<UserDto> findByLogin(String login);

    /**
     * To save user
     * @param userDto user data given from model (from front-end)
     * @throws UserLoginAlreadyExistsException if login already exists
     * @throws UserEmailAlreadyExistsException if email already exists
     * @throws RoleNotFoundException if the role was not found
     */
    void save(UserDto userDto)
            throws UserLoginAlreadyExistsException, UserEmailAlreadyExistsException, RoleNotFoundException;

    /**
     * To change user role
     * @param login username to change role
     * @throws UserNotFoundException if user does not found
     */
    void setSellerRole(String login) throws UserNotFoundException, RoleNotFoundException;

}
