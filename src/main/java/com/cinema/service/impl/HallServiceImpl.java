package com.cinema.service.impl;

import com.cinema.dto.HallDto;
import com.cinema.entity.Hall;
import com.cinema.service.HallService;
import org.springframework.stereotype.Service;

import java.util.Locale;

/**
 * HallServiceImpl service implementation <br>
 * Fulfil variety operation with halls
 * @see HallService
 */
@Service
public class HallServiceImpl implements HallService {

    /**
     * Make a hall dto from the hall entity
     * @param locale for i18n
     * @param hall desired the hall entity to convert
     * @return hall dto
     * @see HallDto
     */
    HallDto getDtoFromEntity(Locale locale, Hall hall) {
        switch (locale.getLanguage()) { // desired lang
            case "ua":
                return HallDto.builder()
                            .hallId(hall.getHallId())
                            .title(hall.getTitleUa())
                        .build();
            default:
                return HallDto.builder()
                            .hallId(hall.getHallId())
                            .title(hall.getTitleEn())
                        .build();
        }
    }
}
