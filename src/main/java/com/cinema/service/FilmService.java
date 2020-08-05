package com.cinema.service;

import com.cinema.dto.FilmDto;
import com.cinema.dto.i18n.FilmLangContentDto;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Film service interface
 */
public interface FilmService {

    /**
     * To find films by released and active
     * @param locale for i18n
     * @param active is current film to be shown
     * @return list of films per language
     */
    List<FilmLangContentDto> findByIsReleasedEquals(Locale locale, Boolean active);

    /**
     * To save film
     * @param filmDto dto to save
     * @throws IOException if an error occurs while converting image to base64 string format to storing in database
     */
    void save(FilmDto filmDto) throws IOException;

}
