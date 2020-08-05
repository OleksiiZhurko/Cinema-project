package com.cinema.dto;

import com.cinema.dto.i18n.FilmLangContentDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.json.JSONArray;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Movie session class dto which represents movie session info <br>
 *     Only to front-end
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
public final class MovieScheduleDto {

    /**
     * Movie session id in Long
     */
    @NotEmpty
    private Long filmTimeId;

    /**
     * LocalDate field which represent date of showing in <i>yyyy-MM-dd</i> format
     */
    @NotEmpty
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date;

    /**
     * LocalTime field which represent time of beginning in <i>HH:mm:ss</i> format
     */
    @NotEmpty
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime startAt;

    /**
     * LocalTime field which represent time of ending in <i>HH:mm:ss</i> format
     */
    @NotEmpty
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime endAt;

    /**
     * Price of session
     */
    @NotEmpty
    private Integer price;

    /**
     * JSON field which consists of 3 main fields: row, seat and occupied
     */
    @NotEmpty
    private JSONArray seats;

    /**
     * Film in showing session
     */
    @NotEmpty
    private FilmLangContentDto film;

    /**
     * Hall in showing session
     */
    @NotEmpty
    private HallDto hall;

}
