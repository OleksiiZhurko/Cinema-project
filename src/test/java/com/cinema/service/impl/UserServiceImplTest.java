package com.cinema.service.impl;

import com.cinema.dto.UserDto;
import com.cinema.entity.User;
import com.cinema.exceptions.UserEmailAlreadyExistsException;
import com.cinema.exceptions.UserLoginAlreadyExistsException;
import com.cinema.repository.UserRepo;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class UserServiceImplTest {

    @MockBean
    private UserRepo userRepo;

    @MockBean
    private RoleServiceImpl roleService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserServiceImpl userService;

    private final UserDto user = UserDto.builder()
            .firstName("forename")
            .lastName("surname")
            .login("login")
            .email("email@something.com")
            .password("password")
            .build();

    @Test
    void saveWithExistingLogin() throws Exception {
        Mockito.doReturn(Optional.of(new User()))
                .when(userRepo)
                .findByLogin("login");

        assertThrows(UserLoginAlreadyExistsException.class, () -> userService.save(user));
    }

    @Test
    void saveWithExistingEmail() throws Exception {
        Mockito.doReturn(Optional.of(new User()))
                .when(userRepo)
                .findByEmail("email@something.com");

        assertThrows(UserEmailAlreadyExistsException.class, () -> userService.save(user));
    }

    @Test
    void getDtoFromEntityWithNull() {
        assertEquals(
                UserDto.builder().build().getFirstName(),
                userService.getDtoFromEntity(User.builder().build()).getFirstName()
        );
    }
}