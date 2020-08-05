package com.cinema.config;

import com.cinema.WhiteBlackApp;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;

import javax.annotation.PostConstruct;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Class configuration
 */
@Configuration
public class AppConfig implements WebMvcConfigurer {

    /**
     * @return Java8TimeDialect is for date in Thymeleaf
     */
    @Bean
    public Java8TimeDialect java8TimeDialect() {
        return new Java8TimeDialect();
    }

    /**
     * @return LocaleResolver that contains base (English) language
     */
    @Bean
    public LocaleResolver localeResolver() {
        CookieLocaleResolver clr = new CookieLocaleResolver();
        clr.setDefaultLocale(Locale.ENGLISH);
        clr.setCookieName("language");

        return clr;
    }

    /**
     * Set timezone
     */
    @PostConstruct
    public void setTimeZone() {
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Kiev"));
    }

    /**
     * @param registry returns the new locale with the selected language
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LocaleChangeInterceptor());
    }
}
