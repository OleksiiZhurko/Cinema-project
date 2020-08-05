package com.cinema.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.security.RolesAllowed;

/**
 * Class controller is for ROLE_ADMIN
 */
@Controller
@RequestMapping("/admin")
@RolesAllowed("ROLE_ADMIN")
public final class AdminController {

    // TODO

}
