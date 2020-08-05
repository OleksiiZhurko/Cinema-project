package com.cinema.entity;

import com.cinema.annotations.converters.ConverterJSONArray;
import lombok.*;
import org.json.JSONArray;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString

@Entity
@Table(name = "halls_schedule")
public final class MovieSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "halls_schedule_id")
    private Long filmTimeId;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(nullable = false, name = "show_date")
    private LocalDate date;

    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    @Column(nullable = false, name = "start_at")
    private LocalTime startAt;

    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    @Column(nullable = false, name = "end_at")
    private LocalTime endAt;

    @Column(nullable = false, name = "price")
    private Integer price;

    @Column(nullable = false, name = "seats")
    @Convert(converter = ConverterJSONArray.class)
    private JSONArray seats;

    @OneToMany(mappedBy = "movieSchedule", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @ToString.Exclude
    private List<Ticket> tickets;

    @ManyToOne
    @JoinColumn(name = "film_id", updatable = true)
    @ToString.Exclude
    private Film film;

    @ManyToOne
    @JoinColumn(name = "hall_id", updatable = false)
    @ToString.Exclude
    private Hall hall;
}
