package com.cinema.service;

import com.cinema.dto.BuyTicketsDto;
import com.cinema.dto.TicketDto;
import com.cinema.exceptions.MovieSessionNotFoundException;
import com.cinema.exceptions.PriceChangeException;
import com.cinema.exceptions.TicketAlreadyBoughtException;
import com.cinema.exceptions.UserNotFoundException;

import java.util.List;
import java.util.Locale;

/**
 * Ticket service interface
 */
public interface TicketService {

    /**
     * To find tickets by user login
     * @param locale for i18n
     * @param login username
     * @return list of tickets
     */
    List<TicketDto> findByUserLogin(Locale locale, String login);

    /**
     * To process buying
     * @param buyTicketsDto selected session and seats
     * @throws MovieSessionNotFoundException if session does not exist
     * @throws UserNotFoundException if user does not found
     * @throws TicketAlreadyBoughtException if some tickets already bought
     * @throws PriceChangeException if prices mismatch
     */
    void save(BuyTicketsDto buyTicketsDto)
            throws MovieSessionNotFoundException, UserNotFoundException,
                TicketAlreadyBoughtException, PriceChangeException;
}
