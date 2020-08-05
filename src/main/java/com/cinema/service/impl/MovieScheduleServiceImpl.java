package com.cinema.service.impl;

import com.cinema.dto.MovieScheduleDto;
import com.cinema.entity.MovieSchedule;
import com.cinema.repository.MovieScheduleRepo;
import com.cinema.service.MovieScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * MovieScheduleServiceImpl service implementation <br>
 * Fulfil variety operation with movies sessions
 * @see MovieScheduleService
 */
@Service
public class MovieScheduleServiceImpl implements MovieScheduleService {

    /**
     * HallServiceImpl field is for performing operation to get halls
     */
    private final HallServiceImpl hallService;

    /**
     * MovieScheduleRepo field is for performing search, save and update the movie session entity operation in database
     */
    private final MovieScheduleRepo movieScheduleRepo;

    /**
     * FilmServiceImpl field is for performing operation to get films
     */
    private final FilmServiceImpl filmService;

    /**
     * Current class constructor
     * @param hallService <b>HallServiceImpl</b> class
     * @param movieScheduleRepo <b>MovieScheduleRepo</b> interface
     * @param filmService <b>FilmServiceImpl</b> class
     */
    @Autowired
    public MovieScheduleServiceImpl(HallServiceImpl hallService,
                                    MovieScheduleRepo movieScheduleRepo,
                                    FilmServiceImpl filmService) {
        this.hallService = hallService;
        this.movieScheduleRepo = movieScheduleRepo;
        this.filmService = filmService;
    }

    /**
     * Movie session search by id
     * @param locale for i18n
     * @param id     for find desired movie session
     * @return Optional of movie session
     * @see MovieScheduleDto
     */
    @Override
    public Optional<MovieScheduleDto> findByFilmTimeId(Locale locale, Long id) {
        return movieScheduleRepo.findByFilmTimeId(id)
                .map(filmTime ->
                        MovieScheduleDto.builder()
                                .filmTimeId(filmTime.getFilmTimeId())
                                .date(filmTime.getDate())
                                .startAt(filmTime.getStartAt())
                                .endAt(filmTime.getEndAt())
                                .price(filmTime.getPrice())
                                .seats(filmTime.getSeats())
                                .film(filmService.getDtoFromEntity(locale, filmTime.getFilm()))
                                .hall(hallService.getDtoFromEntity(locale, filmTime.getHall()))
                        .build() // build movie session object
                );
    }

    /**
     * Find all movies sessions
     * @param locale for i18n
     * @return list of movies sessions
     * @see MovieScheduleDto
     */
    @Override
    public List<MovieScheduleDto> findAll(Locale locale) {
        return movieScheduleRepo.findAll().stream()
                .map(filmTime ->
                        MovieScheduleDto.builder()
                                .filmTimeId(filmTime.getFilmTimeId())
                                .date(filmTime.getDate())
                                .startAt(filmTime.getStartAt())
                                .endAt(filmTime.getEndAt())
                                .price(filmTime.getPrice())
                                .film(filmService.getDtoFromEntity(locale, filmTime.getFilm()))
                                .hall(hallService.getDtoFromEntity(locale, filmTime.getHall()))
                        .build()) // build movie session object
                .collect(Collectors.toList());
    }

    /**
     * Find all movies sessions for current time period
     * @param locale for i18n
     * @return list of movies sessions
     * @see MovieScheduleDto
     */
    @Override
    public List<MovieScheduleDto> findByDateBetween(Locale locale) {
        return movieScheduleRepo.findByDateBetween(LocalDate.now(), LocalDate.now().plusDays(FILM_PERIOD)).stream()
                .map(filmTime ->
                        MovieScheduleDto.builder()
                                .filmTimeId(filmTime.getFilmTimeId())
                                .date(filmTime.getDate())
                                .startAt(filmTime.getStartAt())
                                .endAt(filmTime.getEndAt())
                                .price(filmTime.getPrice())
                                .seats(filmTime.getSeats())
                                .film(filmService.getDtoFromEntity(locale, filmTime.getFilm()))
                                .hall(hallService.getDtoFromEntity(locale, filmTime.getHall()))
                        .build())
                .collect(Collectors.toList());
    }

    /**
     * Save the movie session entity
     * @param movieSchedule movie session entity
     */
    void saveEntity(MovieSchedule movieSchedule) {
        movieScheduleRepo.save(movieSchedule);
    }

    /**
     * Find movie session by id
     * @param id desired id of movie session
     * @return movie session entity
     * @see MovieSchedule
     */
    Optional<MovieSchedule> findByFilmTimeIdEntity(Long id) {
        return movieScheduleRepo.findByFilmTimeId(id);
    }

    /**
     * Make a movie session dto from the movie session entity
     * @param locale for i18n
     * @param movieSchedule desired the movie session entity to convert
     * @return movie session dto
     * @see MovieScheduleDto
     */
    MovieScheduleDto getDtoFromEntity(Locale locale, MovieSchedule movieSchedule) {
        return
                MovieScheduleDto.builder()
                        .date(movieSchedule.getDate())
                        .startAt(movieSchedule.getStartAt())
                        .price(movieSchedule.getPrice())
                        .hall(hallService.getDtoFromEntity(locale, movieSchedule.getHall()))
                        .film(filmService.getDtoFromEntity(locale, movieSchedule.getFilm()))
                .build();
    }
}
