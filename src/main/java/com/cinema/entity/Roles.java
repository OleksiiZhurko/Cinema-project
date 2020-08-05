package com.cinema.entity;

/**
 * Enum which contains roles represented in WhiteBlack cinema project
 */
public enum Roles {
    ROLE_ANONYMOUS,
    ROLE_USER,
    ROLE_SELLER,
    ROLE_ADMIN;

    public String getRole() {
        return this.name().substring(5);
    }
}
