package com.cinema.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.security.RolesAllowed;

/**
 * Class controller is for ROLE_SELLER
 */
@Controller
@RequestMapping("/seller")
@RolesAllowed("ROLE_SELLER")
public final class SellerController {

    // TODO

}
