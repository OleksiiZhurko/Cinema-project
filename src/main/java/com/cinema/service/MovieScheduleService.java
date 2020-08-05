package com.cinema.service;

import com.cinema.dto.MovieScheduleDto;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

/**
 * Movie session service interface
 */
public interface MovieScheduleService {

    /**
     * Range to show schedule for users and sellers
     */
    Integer FILM_PERIOD = 6;

    /**
     * To find movie session by session id
     * @param locale for i18n
     * @param id for find desired movie session
     * @return Optional of movie session
     */
    Optional<MovieScheduleDto> findByFilmTimeId(Locale locale, Long id);

    /**
     * To find all movie sessions
     * @param locale for i18n
     * @return list of movie sessions
     */
    List<MovieScheduleDto> findAll(Locale locale);

    /**
     * To find movie sessions
     * @param locale for i18n
     * @return list of movie sessions for a certain period
     */
    List<MovieScheduleDto> findByDateBetween(Locale locale);

}
