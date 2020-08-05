package com.cinema.repository;

import com.cinema.entity.Hall;
import org.springframework.data.repository.CrudRepository;

/**
 * Hall repository is responsible for manipulation with hall table in the database
 */
public interface HallRepo extends CrudRepository<Hall, Long> {
}
