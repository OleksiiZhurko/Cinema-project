package com.cinema.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.json.JSONArray;

import javax.validation.constraints.NotEmpty;

/**
 * Ticket and additional class dto which participate in buying tickets <br>
 *     Only from front-end
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
public final class BuyTicketsDto {

    /**
     * Movie session id in Long
     */
    @NotEmpty
    private Long sessionId;

    /**
     * User login
     */
    private String login;

    /**
     * Price of ticket
     */
    @NotEmpty
    private Integer price;

    /**
     * JSON field which consists of 3 main fields: row, seat and occupied
     */
    @NotEmpty
    private JSONArray seats;

}
