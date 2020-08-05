package com.cinema.controller;

import com.cinema.service.impl.FilmServiceImpl;
import com.cinema.service.impl.MovieScheduleServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class DefaultControllerTest {

    @MockBean
    private FilmServiceImpl filmService;

    @MockBean
    private MovieScheduleServiceImpl movieScheduleService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DefaultController defaultController;

    @Test
    @DisplayName("DefaultController does not have to be null")
    public void isNullController() {
        assertThat(defaultController).isNotNull();
    }

    @Test
    @DisplayName("Getting a home page")
    void getHomePage() throws Exception {
        this.mockMvc.perform(get("/"))
                .andExpect(status().isOk());

        Mockito.verify(filmService, Mockito.times(2))
                .findByIsReleasedEquals(
                        ArgumentMatchers.any(Locale.class),
                        ArgumentMatchers.anyBoolean()
                );

        Mockito.verify(movieScheduleService, Mockito.times(1))
                .findByDateBetween(
                        ArgumentMatchers.any(Locale.class)
                );
    }

    @Test
    @DisplayName("Auth form for not found")
    void getNotFoundPage() throws Exception {
        this.mockMvc.perform(get("/notFound"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/auth/sign-in"));
    }
}