package com.awbd.lab5.configuration;


import com.example.awbd.services.security.JpaUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@Profile("h2")
public class SecurityJpaConfig {

    private final JpaUserDetailsService userDetailsService;

    public SecurityJpaConfig(JpaUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeRequests(auth -> auth
                                .requestMatchers("/artists").permitAll()
                                .requestMatchers("/audioAlbums").permitAll()
                                .requestMatchers("/audiotracks").hasAnyRole()
                                .requestMatchers("/h2-console").hasRole("ADMIN")
                                .requestMatchers("/lyrics").hasRole("ADMIN")
                                .requestMatchers("/login").permitAll()
                        //.anyRequest().authenticated()
                )
                .userDetailsService(userDetailsService)
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/perform_login")
                .and()
                .exceptionHandling()
                .accessDeniedPage("/access_denied")
                .and()
                .httpBasic(withDefaults())
                .build();
    }
}

