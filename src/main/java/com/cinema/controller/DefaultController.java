package com.cinema.controller;

import com.cinema.config.AppConfig;
import com.cinema.service.FilmService;
import com.cinema.service.MovieScheduleService;
import com.cinema.service.impl.FilmServiceImpl;
import com.cinema.service.impl.MovieScheduleServiceImpl;
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
 * Class controller for anonymous user
 */
@Controller
@RequestMapping(value = "/")
@RolesAllowed("ROLE_ANONYMOUS")
public final class DefaultController {

    private static final Logger log = Logger.getLogger(DefaultController.class);

    /**
     * LocaleResolver field is for i18n data
     * @see AppConfig#localeResolver()
     */
    private final LocaleResolver localeResolver;

    /**
     * FilmService field is for performing some operation related to screened films in the WhiteBlack cinema
     * @see FilmService
     */
    private final FilmService filmService;

    /**
     * MovieScheduleService field is for performing some operation related to session schedule
     * @see MovieScheduleService
     */
    private final MovieScheduleService movieScheduleService;

    /**
     * Current class constructor
     * @param filmService <b>FilmServiceImpl</b> class
     * @param movieScheduleService <b>MovieScheduleServiceImpl</b> class
     * @param localeResolver <b>LocaleResolver</b> class (bean)
     */
    @Autowired
    public DefaultController(
            FilmServiceImpl filmService,
            MovieScheduleServiceImpl movieScheduleService,
            LocaleResolver localeResolver) {
        this.filmService = filmService;
        this.movieScheduleService = movieScheduleService;
        this.localeResolver = localeResolver;
    }

    /**
     * @param model for sending some values to the front-end
     * @param request for extracting current locale language
     * @return the main page
     */
    @GetMapping(value = "")
    public String getHomePage(Model model, HttpServletRequest request) {
        log.info("[GET] Anonymous requested home page at url /");

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
                .addAttribute("FilmTime", movieScheduleService.findByDateBetween(localeLang)); // adding schedule

        return "index";
    }
}
