package com.cinema.service.impl;

import com.cinema.dto.FilmDto;
import com.cinema.dto.i18n.FilmLangContentDto;
import com.cinema.entity.Film;
import com.cinema.repository.FilmRepo;
import com.cinema.service.FilmService;
import com.cinema.service.MovieScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * FilmServiceImpl service implementation <br>
 * Fulfil variety operation with films
 * @see FilmService
 */
@Service
public class FilmServiceImpl implements FilmService {

    /**
     * FilmRepo field is for performing search, save and update the film entity operation in database
     * @see FilmRepo
     */
    private final FilmRepo filmRepo;

    /**
     * Current class constructor
     * @param filmRepo <b>FilmRepo</b> interface
     */
    @Autowired
    public FilmServiceImpl(FilmRepo filmRepo) {
        this.filmRepo = filmRepo;
    }

    /**
     * Find films by released
     * @param locale     for i18n
     * @param isReleased means released or coming soon films
     * @return list of films
     * @see FilmLangContentDto
     */
    @Override
    public List<FilmLangContentDto> findByIsReleasedEquals(Locale locale, Boolean isReleased) {
        switch (locale.getLanguage()) { // desired lang
            case "ua":
                return filmRepo.findByIsReleasedEqualsAndActiveEquals(isReleased, true).stream()
                        .map(elem ->
                                FilmLangContentDto.builder()
                                        .filmId(elem.getFilmId())
                                        .logoImg(elem.getLogoImg())
                                        .publishDate(elem.getPublishDate())
                                        .runningTime(elem.getRunningTime())
                                        .title(elem.getTitleUa())
                                        .starring(elem.getStarringUa())
                                        .director(elem.getDirectorUa())
                                        .text(elem.getTextUa())
                                        .country(elem.getCountryUa())
                                        .genre(elem.getGenre().getGenreUa())
                                        .language(elem.getLanguage().getLangUa())
                                .build())
                        .collect(Collectors.toList());
            default:
                return filmRepo.findByIsReleasedEqualsAndActiveEquals(isReleased, true).stream()
                        .map(elem ->
                                FilmLangContentDto.builder()
                                        .filmId(elem.getFilmId())
                                        .logoImg(elem.getLogoImg())
                                        .publishDate(elem.getPublishDate())
                                        .runningTime(elem.getRunningTime())
                                        .title(elem.getTitleEn())
                                        .starring(elem.getStarringEn())
                                        .director(elem.getDirectorEn())
                                        .text(elem.getTextEn())
                                        .country(elem.getCountryEn())
                                        .genre(elem.getGenre().getGenreEn())
                                        .language(elem.getLanguage().getLangEn())
                                .build())
                        .collect(Collectors.toList());
        }
    }

    /**
     * Try to save film
     * @param filmDto dto to save
     * @throws IOException if the image cannot be converted
     */
    @Override
    public void save(FilmDto filmDto) throws IOException {
         filmRepo.save(
                 Film.builder()
                        .logoImg(filmDto.imageToBase64Format())
                        .publishDate(filmDto.getPublishDate())
                        .runningTime(filmDto.getRunningTime())
                        .titleEn(filmDto.getTitleEn())
                        .titleUa(filmDto.getTitleUa())
                        .starringEn(filmDto.getStarringEn())
                        .starringUa(filmDto.getStarringUa())
                        .directorEn(filmDto.getDirectorEn())
                        .directorUa(filmDto.getDirectorUa())
                        .textEn(filmDto.getTextEn())
                        .textUa(filmDto.getTextUa())
                        .countryEn(filmDto.getCountryEn())
                        .countryUa(filmDto.getCountryUa())
                        .isReleased(filmDto.getIsReleased())
                        .active(filmDto.getActive())
                .build()
        );
    }

    /**
     * Make a film with i18n dto from the film entity
     * @param locale for i18n
     * @param film desired the film entity to convert
     * @return film dto with i18n
     * @see FilmLangContentDto
     */
    FilmLangContentDto getDtoFromEntity(Locale locale, Film film) {
        switch (locale.getLanguage()) { // desired lang
            case "ua":
                return FilmLangContentDto.builder()
                            .filmId(film.getFilmId())
                            .title(film.getTitleUa())
                        .build();
            default:
                return FilmLangContentDto.builder()
                            .filmId(film.getFilmId())
                            .title(film.getTitleEn())
                        .build();
        }
    }
}
