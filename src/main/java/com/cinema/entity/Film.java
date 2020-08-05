package com.cinema.entity;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString

@Entity
@Table(name="films")
public final class Film {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "film_id")
    private Long filmId;

    @Column(nullable = false, name = "logo_img")
    private String logoImg;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(nullable = false, name = "publish_date")
    private LocalDate publishDate;

    @Column(nullable = false, name = "running_time")
    private Integer runningTime;

    @Column(nullable = false, name = "title_en")
    private String titleEn;

    @Column(nullable = false, name = "title_ua")
    private String titleUa;

    @Column(nullable = false, name = "starring_en")
    private String starringEn;

    @Column(nullable = false, name = "starring_ua")
    private String starringUa;

    @Column(nullable = false, name = "director_en")
    private String directorEn;

    @Column(nullable = false, name = "director_ua")
    private String directorUa;

    @Column(nullable = false, name = "text_en")
    private String textEn;

    @Column(nullable = false, name = "text_ua")
    private String textUa;

    @Column(nullable = false, name = "country_en")
    private String countryEn;

    @Column(nullable = false, name = "country_ua")
    private String countryUa;

    @Column(nullable = false, name = "is_released")
    private Boolean isReleased;

    @Column(nullable = false, name = "is_active")
    private Boolean active;

    @OneToMany(mappedBy = "film", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @ToString.Exclude
    private List<MovieSchedule> movieSchedules;

    @ManyToOne
    @JoinColumn(name = "genre_id", updatable = false)
    @ToString.Exclude
    private Genre genre;

    @ManyToOne
    @JoinColumn(name = "film_lang_id", updatable = false)
    @ToString.Exclude
    private Language language;

}
