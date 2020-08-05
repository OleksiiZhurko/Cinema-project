package com.cinema.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

/**
 * User class dto contains user info <br>
 *     To and from front-end
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
public final class UserDto {

    /**
     * Forename of user
     */
    @NotEmpty
    private String firstName;

    /**
     * Surname of user
     */
    @NotEmpty
    private String lastName;

    /**
     * Username of user
     */
    @NotEmpty
    private String login;

    /**
     * Email of user
     */
    @Email
    @NotEmpty
    private String email;

    /**
     * Password of user
     */
    @NotEmpty
    private String password;

}
