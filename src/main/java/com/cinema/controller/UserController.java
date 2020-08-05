package com.cinema.controller;

import com.cinema.config.AppConfig;
import com.cinema.service.impl.FilmServiceImpl;
import com.cinema.service.impl.MovieScheduleServiceImpl;
import com.cinema.service.impl.TicketServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.LocaleResolver;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * Class controller is for ROLE_USER
 */
@Controller
@RequestMapping("/user")
@RolesAllowed("ROLE_USER")
public final class UserController {

    private static final Logger log = Logger.getLogger(UserController.class);

    /**
     * LocaleResolver field is for i18n data
     * @see AppConfig#localeResolver()
     */
    private final LocaleResolver localeResolver;

    /**
     * FilmServiceImpl field is for performing some operation related to screened films in the WhiteBlack cinema
     * @see FilmServiceImpl
     */
    private final FilmServiceImpl filmService;

    /**
     * MovieScheduleServiceImpl field is for performing some operation related to session schedule
     * @see MovieScheduleServiceImpl
     */
    private final MovieScheduleServiceImpl movieScheduleService;

    /**
     * TicketServiceImpl field is for performing some operation related to show purchased tickets
     * @see TicketServiceImpl
     */
    private final TicketServiceImpl ticketService;

    /**
     * Current class constructor
     * @param filmService <b>FilmServiceImpl</b> class
     * @param movieScheduleService <b>MovieScheduleServiceImpl</b> class
     * @param ticketService <b>TicketServiceImpl</b> class
     * @param localeResolver <b>LocaleResolver</b> class (bean)
     */
    @Autowired
    public UserController(
            FilmServiceImpl filmService,
            MovieScheduleServiceImpl movieScheduleService,
            TicketServiceImpl ticketService,
            LocaleResolver localeResolver) {
        this.filmService = filmService;
        this.movieScheduleService = movieScheduleService;
        this.ticketService = ticketService;
        this.localeResolver = localeResolver;
    }

    /**
     * @param model for sending some values to the front-end
     * @param request for extracting current locale language and extracting user login
     * @return the main page
     */
    @GetMapping(value = "")
    public String getHomePage(Model model, HttpServletRequest request) {
        String login = request.getUserPrincipal().getName(); // extraction user login

        log.info(String.format("[GET] [%s] requested home page at url /user", login));

        Locale localeLang = localeResolver.resolveLocale(request); // get rid of code duplication

        model
                .addAttribute(
                        "ReleasedFilm",
                        filmService.findByIsReleasedEquals(localeLang, true)
                ) // adding released films
                .addAttribute(
                        "ComingSoonFilm",
                        filmService.findByIsReleasedEquals(localeLang, false)
                ) // adding coming soon films
                .addAttribute("FilmTime", movieScheduleService.findByDateBetween(localeLang)) // adding schedule
                .addAttribute("Tickets", ticketService.findByUserLogin(localeLang, login)) // adding bought tickets
                .addAttribute("Login", login); // adding user login

        return "index";
    }
}
