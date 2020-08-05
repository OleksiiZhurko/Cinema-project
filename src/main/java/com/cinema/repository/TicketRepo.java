package com.cinema.repository;

import com.cinema.entity.Ticket;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Ticket repository is responsible for manipulation with tickets table in the database
 */
public interface TicketRepo extends CrudRepository<Ticket, Long> {

    /**
     * Find the user by ticket id
     * @param login username
     * @return list of tickets in descending order
     */
    List<Ticket> findByUserLoginOrderByTicketIdDesc(String login);

}
