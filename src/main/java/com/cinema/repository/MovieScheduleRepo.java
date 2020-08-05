package com.cinema.repository;

import com.cinema.entity.MovieSchedule;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Movie session repository is responsible for manipulation with halls_schedule table in the database
 */
public interface MovieScheduleRepo extends CrudRepository<MovieSchedule, Long> {

    /**
     * Find all movies sessions
     * @return list of movies sessions
     */
    List<MovieSchedule> findAll();

    /**
     * Find movies sessions by time period
     * @param date1 date means from (>=)
     * @param date2 date means to (<=)
     * @return list of movies sessions
     */
    List<MovieSchedule> findByDateBetween(LocalDate date1, LocalDate date2);

    /**
     * Find the movie session
     * @param id desired id of the movie session
     * @return Optional of desired movie session
     */
    Optional<MovieSchedule> findByFilmTimeId(Long id);
}
