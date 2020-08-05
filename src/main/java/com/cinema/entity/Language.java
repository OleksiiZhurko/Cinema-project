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
@Table(name = "film_langs")
public final class Language {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "film_lang_id")
    private Long langId;

    @Column(nullable = false, name = "film_lang_en")
    private String langEn;

    @Column(nullable = false, name = "film_lang_ua")
    private String langUa;

    @OneToMany(mappedBy = "language", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @ToString.Exclude
    private List<Film> films;
}
