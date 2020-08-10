package com.cinema.controller;

import com.cinema.dto.UserDto;
import com.cinema.entity.User;
import com.cinema.exceptions.RoleNotFoundException;
import com.cinema.exceptions.UserEmailAlreadyExistsException;
import com.cinema.exceptions.UserLoginAlreadyExistsException;
import com.cinema.service.UserService;
import com.cinema.service.impl.UserServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.security.RolesAllowed;

/**
 * Class controller is for authentication
 */
@Controller
@RequestMapping("/auth")
@RolesAllowed("ROLE_ANONYMOUS")
public final class AuthController {

    private static final Logger log = Logger.getLogger(AuthController.class);

    /**
     * UserService class is for performing some operation related to authentication
     * @see UserService
     */
    private final UserService userService;

    /**
     * Current class constructor
     * @param userService <b>UserServiceImpl</b> class
     */
    @Autowired
    public AuthController(UserServiceImpl userService) {
        this.userService = userService;
    }

    /**
     * @return the page which contains login form
     */
    @GetMapping(value = "/sign-in")
    public String getSignInForm() {
        log.info("[GET] Anonymous    requested sign in at url /auth/sign-in");

        return "sign_in";
    }

    /**
     * @param model for sending some values to the front-end
     * @return the page which contains registration form
     */
    @GetMapping(value = "/sign-up")
    public String getSignUpForm(Model model) {
        log.info("[GET] Anonymous requested sign up at url /auth/sign-up");

        model.addAttribute("user", new User()); // adding a user to save

        return "sign_up";
    }

    /**
     * @param userDto dto is for processing user data given from the front-end
     * @param model for sending some values to the front-end
     * @return redirect String value to the login page (if registration form was filed successfully,
     *                                                  unless gives the page which contains registration form)
     */
    @PostMapping(value = "/save_user")
    public String addUser(@ModelAttribute("user") UserDto userDto, Model model) {
        log.info("[POST] Adding a new user");

        try {
            userService.save(userDto); // trying to save
        } catch (UserLoginAlreadyExistsException userLoginAlreadyExists) {
            log.warn(String.format("[%s] Login already exists", userDto.getLogin()));
            model.addAttribute("userExists", userDto.getLogin()); // login exists
            return "sign_up";
        } catch (UserEmailAlreadyExistsException userEmailAlreadyExists) {
            log.warn(String.format("[%s] Email already exists", userDto.getEmail()));
            model.addAttribute("emailExists", userDto.getEmail()); // email exists
            return "sign_up";
        } catch (RoleNotFoundException e) {
            log.error("Roles error occurred while saving user");
            model.addAttribute("roleError", "Role doesn't exists!"); // role not found
            return "sign_up";
        }

        log.info(String.format("User [%s] added successfully", userDto.getLogin()));

        return "redirect:/auth/sign-in";
    }
}
