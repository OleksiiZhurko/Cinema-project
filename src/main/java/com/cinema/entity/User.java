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
@Table(name = "users")
public final class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "user_id")
    private Long userId;

    @Column(nullable = false, name = "forename")
    private String firstName;

    @Column(nullable = false, name = "surname")
    private String lastName;

    @Column(nullable = false, unique = true, name = "login")
    private String login;

    @Column(nullable = false, unique = true, name = "email")
    private String email;

    @Column(nullable = false, name = "parole")
    private String password;

    @Column(nullable = false, name = "is_active")
    private Boolean active;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @ToString.Exclude
    private List<Ticket> tickets;

    @ManyToOne
    @JoinColumn(name = "role_id", updatable = false, insertable = true)
    @ToString.Exclude
    private Role role;
}
