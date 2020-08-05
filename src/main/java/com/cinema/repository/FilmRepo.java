package com.cinema.repository;

import com.cinema.entity.Film;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Film repository is responsible for manipulation with film table in the database
 */
public interface FilmRepo extends CrudRepository<Film, Long> {

    /**
     * Find films by toggles such a isReleased and active
     * @param isReleased means released or coming soon films
     * @param active (in)active film
     * @return list of films
     * @see Film
     */
    List<Film> findByIsReleasedEqualsAndActiveEquals(Boolean isReleased, Boolean active);

}
