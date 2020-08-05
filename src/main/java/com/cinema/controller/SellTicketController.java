package com.cinema.controller;

import com.cinema.config.AppConfig;
import com.cinema.dto.BuyTicketsDto;
import com.cinema.dto.MovieScheduleDto;
import com.cinema.exceptions.MovieSessionNotFoundException;
import com.cinema.exceptions.PriceChangeException;
import com.cinema.exceptions.TicketAlreadyBoughtException;
import com.cinema.exceptions.UserNotFoundException;
import com.cinema.service.impl.MovieScheduleServiceImpl;
import com.cinema.service.impl.TicketServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.LocaleResolver;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

/**
 * Controller class is for displaying halls and buying tickets
 */
@Controller
@RequestMapping("/tickets")
@RolesAllowed({"ROLE_USER", "ROLE_SELLER", "ROLE_ADMIN"})
public final class SellTicketController {

    private static final Logger log = Logger.getLogger(SellTicketController.class);

    /**
     * LocaleResolver field is for i18n data
     * @see AppConfig#localeResolver()
     */
    private final LocaleResolver localeResolver;

    /**
     * TicketServiceImpl field is for performing some operation related to buy some tickets
     * @see TicketServiceImpl
     */
    private final TicketServiceImpl ticketService;

    /**
     * MovieScheduleServiceImpl field is for performing some operation related to session schedule
     * @see MovieScheduleServiceImpl
     */
    private final MovieScheduleServiceImpl movieScheduleService;

    /**
     * Current class constructor
     * @param ticketService <b>TicketServiceImpl</b> class
     * @param movieScheduleService <b>MovieScheduleServiceImpl</b> class
     * @param localeResolver <b>LocaleResolver</b> class (bean)
     */
    @Autowired
    public SellTicketController(TicketServiceImpl ticketService,
                                MovieScheduleServiceImpl movieScheduleService,
                                LocaleResolver localeResolver) {
        this.ticketService = ticketService;
        this.movieScheduleService = movieScheduleService;
        this.localeResolver = localeResolver;
    }

    /**
     * Find requesting film session
     * @param sessionId session film which want to see
     * @param model for sending some values to the front-end
     * @param request for extracting current locale language
     * @return the page which contains desired film session or main page if not found
     */
    @GetMapping(value = "/session")
    public String getTickets(@RequestParam(value = "id") Long sessionId,
                             Model model,
                             HttpServletRequest request) {
        log.info(String.format("[GET] Movie session [%d] requested at url /tickets/session", sessionId));

        Optional<MovieScheduleDto> movieSchedule = movieScheduleService.findByFilmTimeId(
                localeResolver.resolveLocale(request),
                sessionId
        ); // trying to find desired session id

        if (movieSchedule.isPresent()) { // is not null
            if (movieSchedule.get().getDate().compareTo(LocalDate.now()) == 0) { // suits date
                if (movieSchedule.get().getStartAt().compareTo(LocalTime.now()) > 0) { // suits time
                    log.info(String.format("Session id [%d] is found", sessionId));
                    model.addAttribute("Tickets", movieSchedule.get()); // add session info
                    return "light_hall";
                }
            } else if (movieSchedule.get().getDate().compareTo(LocalDate.now()) > 0) { // suits date
                log.info(String.format("Session id [%d] is found", sessionId));
                model.addAttribute("Tickets", movieSchedule.get()); // add session info
                return "light_hall";
            }
            log.warn(String.format("Session id [%d] outdated", sessionId));
        }
        log.warn(String.format("Session id [%d] is not found", sessionId));

        return "redirect:/";
    }

    /**
     * @param buyTicketsDto dto is for processing tickets given from the front-end
     * @param model for sending some values to the front-end
     * @param request for extracting user login
     * @return the page which contains desired film session or main page if not found
     */
    @PostMapping(value = "/buy")
    public String buyTickets(@RequestBody BuyTicketsDto buyTicketsDto,
                             Model model,
                             HttpServletRequest request) {
        String login = request.getUserPrincipal().getName(); // extraction user login

        buyTicketsDto.setLogin(login); // setting the current user login

        log.info(String.format("[POST] [%s] trying to buy [%d] tickets for [%d] ₴ for [%d] movie session",
                login,
                buyTicketsDto.getSeats().length(),
                buyTicketsDto.getPrice(),
                buyTicketsDto.getSessionId()
        ));

        try {
            ticketService.save(buyTicketsDto); // trying to buy tickets
        } catch (MovieSessionNotFoundException e) {
            log.warn(String.format("Session id [%d] is not found", buyTicketsDto.getSessionId()));
            return "redirect:/redirect/"; // movie does not found
        } catch (UserNotFoundException e) {
            log.warn(String.format("User [%s] is not found", login));
            return "redirect:/auth/sign-in"; // user does not found
        } catch (TicketAlreadyBoughtException e) {
            log.warn(String.format("Some tickets for the session [%d] have already been purchased",
                    buyTicketsDto.getSessionId()
            ));
            model.addAttribute("Bought", "You're late"); // some tickets already bought
            return String.format("redirect:/tickets/session?id=%d", buyTicketsDto.getSessionId());
        } catch (PriceChangeException e) {
            log.warn(String.format("Change in session [%d] prices", buyTicketsDto.getSessionId()));
            model.addAttribute("UpdatePrice", "Price update"); // prices were change
            return String.format("redirect:/tickets/session?id=%d", buyTicketsDto.getSessionId());
        }

        log.info(String.format("Tickets were successfully purchased for session [%d]", buyTicketsDto.getSessionId()));

        return String.format("redirect:/tickets/session?id=%d", buyTicketsDto.getSessionId());
    }
}
