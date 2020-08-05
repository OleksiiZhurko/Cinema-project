package com.cinema.service.impl;

import com.cinema.dto.BuyTicketsDto;
import com.cinema.dto.TicketDto;
import com.cinema.entity.MovieSchedule;
import com.cinema.entity.Ticket;
import com.cinema.entity.User;
import com.cinema.exceptions.MovieSessionNotFoundException;
import com.cinema.exceptions.PriceChangeException;
import com.cinema.exceptions.TicketAlreadyBoughtException;
import com.cinema.exceptions.UserNotFoundException;
import com.cinema.repository.TicketRepo;
import com.cinema.service.TicketService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * Ticket service implementation <br>
 * Fulfil variety operation with tickets
 * @see TicketService
 */
@Service
public class TicketServiceImpl implements TicketService {

    /**
     * TicketRepo field is for performing search and save the ticket entity operation in database
     * @see TicketRepo
     */
    private final TicketRepo ticketRepo;

    /**
     * UserServiceImpl field is for performing operation to get user
     * @see UserServiceImpl
     */
    private final UserServiceImpl userService;

    /**
     * MovieScheduleServiceImpl field is for performing operation to get movie schedule
     * @see MovieScheduleServiceImpl
     */
    private final MovieScheduleServiceImpl movieScheduleService;

    /**
     * Current class constructor
     * @param ticketRepo <b>TicketRepo</b> interface
     * @param userService <b>UserServiceImpl</b> class
     * @param movieScheduleService <b>MovieScheduleServiceImpl</b> class
     */
    @Autowired
    public TicketServiceImpl(TicketRepo ticketRepo,
                             UserServiceImpl userService,
                             MovieScheduleServiceImpl movieScheduleService) {
        this.ticketRepo = ticketRepo;
        this.userService = userService;
        this.movieScheduleService = movieScheduleService;
    }

    /**
     * Find tickets that belong to concrete user
     * @param locale for i18n
     * @param login  username
     * @return list of tickets owned by a concrete user
     * @see TicketDto
     */
    @Override
    public List<TicketDto> findByUserLogin(Locale locale, String login) {
        return
                ticketRepo.findByUserLoginOrderByTicketIdDesc(login).stream()
                        .map(elem ->
                                TicketDto.builder()
                                        .ticketId(elem.getTicketId())
                                        .date(elem.getDate())
                                        .row(elem.getRow())
                                        .seat(elem.getSeat())
                                        .movieSchedule(
                                                movieScheduleService.getDtoFromEntity(
                                                        locale,
                                                        elem.getMovieSchedule()
                                                )
                                        )
                                        .user(userService.getDtoFromEntity(elem.getUser()))
                                .build()) // build ticket object
                        .collect(Collectors.toList());
    }

    /**
     * Process of buying tickets
     * @param buyTicketsDto selected session and seats
     * @throws MovieSessionNotFoundException if movie session does not found
     * @throws UserNotFoundException if user does not found
     * @throws TicketAlreadyBoughtException if some tickets already bought
     * @throws PriceChangeException if prices mismatch
     */
    @Override
    @Transactional
    public void save(BuyTicketsDto buyTicketsDto)
            throws MovieSessionNotFoundException, UserNotFoundException,
                    TicketAlreadyBoughtException, PriceChangeException {

        MovieSchedule movieSchedule =
                movieScheduleService.findByFilmTimeIdEntity(buyTicketsDto.getSessionId())
                        .orElseThrow(MovieSessionNotFoundException::new); // find movie session

        if (!movieSchedule.getPrice().equals(buyTicketsDto.getPrice())) {
            throw new PriceChangeException();
        }

        User user = userService.findByLoginEntity(buyTicketsDto.getLogin()).orElseThrow(UserNotFoundException::new); // find user

        JSONArray buySeats = buyTicketsDto.getSeats(); // get rid of code duplication

        for (int i = 0; i < buySeats.length(); i++) {
            JSONObject seat = buySeats.getJSONObject(i); // extracting JSONObject

            if ((Boolean) movieSchedule.getSeats().getJSONObject((Integer) seat.get("index")).get("occupied")) { // check if the place is bought
                throw new TicketAlreadyBoughtException();
            }
        }

        List<Ticket> tickets = new ArrayList<>(); // array to save

        buySeats.forEach(
                elem -> {
                    JSONObject seat = (JSONObject) elem;

                    movieSchedule.getSeats().getJSONObject((Integer) seat.get("index")).put("occupied", true); // update JSONArray of movie session

                    tickets.add(
                            Ticket.builder()
                                    .row((Integer) seat.get("row"))
                                    .seat((Integer) seat.get("seat"))
                                    .date(
                                            LocalDateTime.parse(
                                                    LocalDateTime.now().toString(),
                                                    DateTimeFormatter.ISO_LOCAL_DATE_TIME
                                            ) // setting purchase time
                                    )
                                    .user(user)
                                    .movieSchedule(movieSchedule)
                            .build()
                    );
                }
        );

        ticketRepo.saveAll(tickets); // saving
    }
}
