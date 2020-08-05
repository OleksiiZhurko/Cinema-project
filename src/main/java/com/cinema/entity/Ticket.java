package com.cinema.entity;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString

@Entity
@Table(name = "tickets")
public final class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "ticket_id")
    private Long ticketId;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column(nullable = false, name = "date_time")
    private LocalDateTime date;

    @Column(nullable = false, name = "seat_row")
    private Integer row;

    @Column(nullable = false, name = "seat_place")
    private Integer seat;

    @ManyToOne
    @JoinColumn(name = "user_id", updatable = true, insertable = true)
    @ToString.Exclude
    private User user;

    @ManyToOne
    @JoinColumn(name = "halls_schedule_id", updatable = true, insertable = true)
    @ToString.Exclude
    private MovieSchedule movieSchedule;
}
