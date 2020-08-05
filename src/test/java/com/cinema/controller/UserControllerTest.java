package com.cinema.controller;

import com.cinema.service.impl.FilmServiceImpl;
import com.cinema.service.impl.MovieScheduleServiceImpl;
import com.cinema.service.impl.TicketServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class UserControllerTest {

    @MockBean
    private FilmServiceImpl filmService;

    @MockBean
    private MovieScheduleServiceImpl movieScheduleService;

    @MockBean
    private TicketServiceImpl ticketService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserController userController;

    @Autowired
    private DefaultController defaultController;

    @Test
    @DisplayName("UserController does not have to be null")
    public void isNullController() {
        assertThat(userController).isNotNull();
    }
}