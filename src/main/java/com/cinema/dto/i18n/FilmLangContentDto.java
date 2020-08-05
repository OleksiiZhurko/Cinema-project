package com.cinema.dto.i18n;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

/**
 * Film class dto which depends on required language <br>
 *     Only to front-end
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
public final class FilmLangContentDto {

    /**
     * Film id in Long
     */
    @NotEmpty
    private Long filmId;

    /**
     * Image in String representation according to base64
     */
    @NotEmpty
    private String logoImg;

    /**
     * LocalDate field which represent date of publishing in <i>yyyy-MM-dd</i> format
     */
    @NotEmpty
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate publishDate;

    /**
     * Positive Integer value which contains running film time
     */
    @NotEmpty
    private Integer runningTime;

    /**
     * Title of film
     */
    @NotEmpty
    private String title;

    /**
     * List of stars who participate in the film in String
     */
    @NotEmpty
    private String starring;

    /**
     * List of directors who participate in the film in String
     */
    @NotEmpty
    private String director;

    /**
     * Info about film
     */
    @NotEmpty
    private String text;

    /**
     * List of countries where film was created in String
     */
    @NotEmpty
    private String country;

    /**
     * List of genres to which the film belongs (one genre now)
     */
    @NotEmpty
    private String genre;

    /**
     * Film language
     */
    @NotEmpty
    private String language;

}
