package com.cinema.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString

@Entity
@Table(name = "halls_info")
public final class Hall {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "hall_id")
    private Long hallId;

    @Column(nullable = false, name = "title_en")
    private String titleEn;

    @Column(nullable = false, name = "title_ua")
    private String titleUa;

    @Column(nullable = false, name = "count_rows")
    private Integer rows;

    @Column(nullable = false, name = "count_seats")
    private Integer seats;

    @OneToMany(mappedBy = "hall", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @ToString.Exclude
    private List<MovieSchedule> movieSchedules;
}
