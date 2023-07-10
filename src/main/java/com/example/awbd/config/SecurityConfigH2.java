package com.example.awbd.config;

import com.example.awbd.services.security.H2UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@Profile("h2")
public class SecurityConfigH2 {

    private final H2UserDetailsService userDetailsService;

    public SecurityConfigH2(H2UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    // creare bean NoOpPasswordEncoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    // bean securityfilterchanin:
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf().disable()
                .cors().disable()
                .exceptionHandling().and()
                .authorizeRequests(auth -> auth
                        .requestMatchers("/h2-console/**").hasAuthority("ADMIN")
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/admin-**").hasAuthority("ADMIN")
                        .requestMatchers("/show-**").hasAnyAuthority("ADMIN", "USER")
                )
                .userDetailsService(this.userDetailsService)
                .headers(headers -> headers.frameOptions().sameOrigin())
                .formLogin().loginPage("/login").loginProcessingUrl("/perform_login").defaultSuccessUrl("/", true)
                .and()
                .exceptionHandling().accessDeniedPage("/access_denied")
                .and()
                .httpBasic(withDefaults())
                .build();
    }
}
