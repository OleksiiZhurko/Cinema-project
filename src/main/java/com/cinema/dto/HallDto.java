package com.cinema.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

/**
 * Hall class dto is for halls info <br>
 *     Only to front-end
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
public final class HallDto {

    /**
     * Hall id in Long
     */
    @NotEmpty
    private Long hallId;

    /**
     * Title of hall
     */
    @NotEmpty
    private String title;

    /**
     * Amount of rows
     */
    @NotEmpty
    private Integer rows;

    /**
     * Amount of seats
     */
    @NotEmpty
    private Integer seats;

}
