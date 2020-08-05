package com.cinema.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Base64;
import java.util.Objects;

/**
 * Film class dto is for saving a new film and updating existing one <br>
 *     To and from front-end
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
public final class FilmDto {

    /**
     * Image file
     */
    @NotEmpty
    private MultipartFile logoImg;

    /**
     * LocalDate field which represent date of publishing in <i>yyyy-MM-dd</i> format
     */
    @NotEmpty
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate publishDate;

    /**
     * Positive Integer value which contains running film time
     */
    @NotEmpty
    private Integer runningTime;

    /**
     * Title of film in English
     */
    @NotEmpty
    private String titleEn;

    /**
     * Title of film in Ukrainian
     */
    @NotEmpty
    private String titleUa;

    /**
     * List of stars who participate in the film in String in English
     */
    @NotEmpty
    private String starringEn;

    /**
     * List of stars who participate in the film in String in Ukrainian
     */
    @NotEmpty
    private String starringUa;

    /**
     * List of directors who participate in the film in String in English
     */
    @NotEmpty
    private String directorEn;

    /**
     * List of directors who participate in the film in String in Ukrainian
     */
    @NotEmpty
    private String directorUa;

    /**
     * Info about film in English
     */
    @NotEmpty
    private String textEn;

    /**
     * Info about film in Ukrainian
     */
    @NotEmpty
    private String textUa;

    /**
     * List of countries where film was created in String in English
     */
    @NotEmpty
    private String countryEn;

    /**
     * List of countries where film was created in String in Ukrainian
     */
    @NotEmpty
    private String countryUa;

    /**
     * Toggle is for released and coming soon films
     */
    @NotEmpty
    private Boolean isReleased;

    /**
     * Toggle is for (in)active films to show
     */
    @NotEmpty
    private Boolean active;

    /**
     * Try to encode incoming image to String according to base64
     * @return encoded image in String representation
     * @throws IOException if the image cannot be converted
     */
    public String imageToBase64Format() throws IOException {
        String originalFilename = Objects.requireNonNull(logoImg.getOriginalFilename());

        return "data:image/" +
                originalFilename.substring(originalFilename.lastIndexOf('.') + 1) +
                ";base64," +
                Base64.getEncoder().encodeToString(logoImg.getBytes());
    }

}
