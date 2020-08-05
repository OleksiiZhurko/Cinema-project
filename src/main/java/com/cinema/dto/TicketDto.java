package com.cinema.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

/**
 * Ticket class dto contains info about bought ticket <br>
 *     Only to front-end
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
public final class TicketDto {

    /**
     * Ticket id in Long
     */
    @NotEmpty
    private Long ticketId;

    /**
     * LocalDate field which represent date of showing in <i>yyyy-MM-dd</i> format
     */
    @NotEmpty
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime date;

    /**
     * Row in hall
     */
    @NotEmpty
    private Integer row;

    /**
     * Seat in hall
     */
    @NotEmpty
    private Integer seat;

    /**
     * User who belongs ticket
     */
    @NotEmpty
    private UserDto user;

    /**
     * Time to start of film
     */
    @NotEmpty
    private MovieScheduleDto movieSchedule;

}
