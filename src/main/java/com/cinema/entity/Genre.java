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
@Table(name = "film_genres")
public final class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "genre_id")
    private Long genreId;

    @Column(nullable = false, name = "genre_en")
    private String genreEn;

    @Column(nullable = false, name = "genre_ua")
    private String genreUa;

    @OneToMany(mappedBy = "genre", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @ToString.Exclude
    private List<Film> films;
}
