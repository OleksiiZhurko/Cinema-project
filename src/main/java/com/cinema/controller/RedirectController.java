package com.cinema.controller;

import com.cinema.entity.Roles;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;

/**
 * Controller is for redirecting
 */
@Controller
@RequestMapping(value = "/redirect")
@RolesAllowed({"ROLE_USER", "ROLE_SELLER", "ROLE_ADMIN"})
public final class RedirectController {

    /**
     * @param request to get user role
     * @return url for current user in role or anonymous
     */
    @GetMapping(value = "")
    public String redirect(HttpServletRequest request) {
        return request.isUserInRole(Roles.ROLE_USER.getRole()) ? "redirect:/user" :
                request.isUserInRole(Roles.ROLE_SELLER.getRole()) ? "redirect:/seller" :
                        request.isUserInRole(Roles.ROLE_ADMIN.getRole()) ? "redirect:/admin" : "redirect:/";
    }
}
