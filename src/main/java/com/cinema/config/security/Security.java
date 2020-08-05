package com.cinema.config.security;

import com.cinema.entity.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

/**
 * Class configuration stands for safety cinema
 * @see WebSecurityConfigurerAdapter
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class Security extends WebSecurityConfigurerAdapter {

    /**
     * PasswordEncoder field is for encode the user password
     * @see Password#passwordEncoder()
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * DataSource field is for connection to database
     * @see DataSource
     */
    private final DataSource dataSource;

    /**
     * Current class constructor
     * @param passwordEncoder <b>PasswordEncoder</b> interface
     * @param dataSource <b>DataSource</b> interface
     */
    @Autowired
    public Security(PasswordEncoder passwordEncoder, DataSource dataSource) {
        this.passwordEncoder = passwordEncoder;
        this.dataSource = dataSource;
    }

    /**
     * Manage security concepts of the project
     * @param http stands for setting of project
     * @throws Exception when something went wrong
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/css/**", "/js/**")
                    .permitAll()
                .antMatchers("/tickets/**")
                    .hasAnyRole(
                            Roles.ROLE_ANONYMOUS.getRole(),
                            Roles.ROLE_USER.getRole(),
                            Roles.ROLE_SELLER.getRole()
                    )
                .antMatchers("/redirect/*")
                    .hasAnyRole(
                            Roles.ROLE_USER.getRole(),
                            Roles.ROLE_SELLER.getRole(),
                            Roles.ROLE_ADMIN.getRole()
                    )
                .antMatchers("/", "/auth/**")
                    .hasRole(Roles.ROLE_ANONYMOUS.getRole())
                .antMatchers("/admin/**")
                    .hasRole(Roles.ROLE_ADMIN.getRole())
                .antMatchers("/user/**")
                    .hasRole(Roles.ROLE_USER.getRole())
                .antMatchers("/seller/**")
                    .hasRole(Roles.ROLE_SELLER.getRole())
                .anyRequest()
                .authenticated()
            .and()
                .exceptionHandling()
                    .accessDeniedPage("/redirect")
            .and()
                .formLogin()
                    .loginPage("/auth/sign-in")
                        .defaultSuccessUrl("/redirect", true)
                        .usernameParameter("username")
                        .passwordParameter("password")
            .and()
                .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                    .clearAuthentication(true)
                    .invalidateHttpSession(true)
                    .logoutSuccessUrl("/");
    }

    /**
     * Responsible for the correct operation of the login form
     * @param auth stands for user authentication
     * @throws Exception when something went wrong
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(passwordEncoder)
                .usersByUsernameQuery(
                        "SELECT login, parole, is_active FROM users " +
                                "WHERE login = ?"
                )
                .authoritiesByUsernameQuery(
                        "SELECT login, in_role FROM users " +
                                "INNER JOIN roles USING(role_id) " +
                                "WHERE login = ?"
                );
    }
}
