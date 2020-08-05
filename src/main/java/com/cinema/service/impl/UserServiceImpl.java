package com.cinema.service.impl;

import com.cinema.dto.UserDto;
import com.cinema.entity.Roles;
import com.cinema.entity.User;
import com.cinema.exceptions.RoleNotFoundException;
import com.cinema.exceptions.UserEmailAlreadyExistsException;
import com.cinema.exceptions.UserLoginAlreadyExistsException;
import com.cinema.exceptions.UserNotFoundException;
import com.cinema.repository.UserRepo;
import com.cinema.config.security.Password;
import com.cinema.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * User service implementation <br>
 * Fulfil variety operation with users
 * @see UserService
 */
@Service
public class UserServiceImpl implements UserService {

    /**
     * RoleServiceImpl field is for performing operation to get roles
     * @see RoleServiceImpl
     */
    private final RoleServiceImpl roleService;

    /**
     * UserRepo field is for performing search, save and update the user entity operation in database
     * @see UserRepo
     */
    private final UserRepo userRepo;

    /**
     * PasswordEncoder field is for encode the user password
     * @see Password#passwordEncoder()
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * Current class constructor
     * @param roleService <b>RoleServiceImpl</b> class
     * @param userRepo <b>UserRepo</b> interface
     * @param passwordEncoder <b>PasswordEncoder</b> interface
     */
    @Autowired
    public UserServiceImpl(RoleServiceImpl roleService, UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.roleService = roleService;
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Find all users
     * @return list of users
     * @see UserDto
     */
    @Override
    public List<UserDto> findAll() {
        return userRepo.findAll().stream()
                .map(user ->
                        UserDto.builder()
                                .firstName(user.getFirstName())
                                .lastName(user.getLastName())
                                .email(user.getEmail())
                                .login(user.getLogin())
                        .build()
                )
                .collect(Collectors.toList());
    }

    /**
     * Find user by login
     * @param login username
     * @return Optional of user dto
     * @see UserDto
     */
    @Override
    public Optional<UserDto> findByLogin(String login) {
        return userRepo.findByLogin(login)
                .map(user ->
                        UserDto.builder()
                                .firstName(user.getFirstName())
                                .lastName(user.getLastName())
                                .email(user.getEmail())
                                .login(user.getLogin())
                        .build()
                );
    }

    /**
     * Try to save a new user
     * @param userDto user data given from model (from front-end)
     * @throws UserLoginAlreadyExistsException if login already exists
     * @throws UserEmailAlreadyExistsException if email already exists
     * @throws RoleNotFoundException if the role was not found
     */
    @Override
    @Transactional
    public void save(UserDto userDto)
            throws UserLoginAlreadyExistsException, UserEmailAlreadyExistsException, RoleNotFoundException {
        if (userRepo.findByLogin(userDto.getLogin()).isPresent()) {
            throw new UserLoginAlreadyExistsException();
        } else if (userRepo.findByEmail(userDto.getEmail()).isPresent()) {
            throw new UserEmailAlreadyExistsException();
        } else {
            userRepo.save(
                    User.builder()
                            .firstName(userDto.getFirstName())
                            .lastName(userDto.getLastName())
                            .login(userDto.getLogin())
                            .email(userDto.getEmail())
                            .password(passwordEncoder.encode(userDto.getPassword()))
                            .role(roleService.findByRole(Roles.ROLE_USER.name()).orElseThrow(RoleNotFoundException::new))
                            .active(true)
                    .build()
            );
        }
    }

    /**
     * Try to find user and replace ROLE_USER with ROLE_SELLER
     * @param login username to change role
     * @throws UserNotFoundException if user does not found
     * @throws RoleNotFoundException if the role was not found
     */
    @Override
    public void setSellerRole(String login) throws UserNotFoundException, RoleNotFoundException {
        User user = userRepo.findByLogin(login).orElseThrow(UserNotFoundException::new);
        user.setRole(roleService.findByRole(Roles.ROLE_SELLER.name()).orElseThrow(RoleNotFoundException::new));
        userRepo.save(user);
    }

    /**
     * Transform entity into dto
     * @param user entity is for transform
     * @return user dto
     * @see UserDto
     */
    UserDto getDtoFromEntity(User user) {
        return UserDto.builder()
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                .build();
    }

    /**
     * Find a user by login directly (entity)
     * @param login username
     * @return Optional of user entity
     * @see User
     */
    Optional<User> findByLoginEntity(String login) {
        return userRepo.findByLogin(login);
    }
}
