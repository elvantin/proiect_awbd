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

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("guest")
                .password(passwordEncoder.encode("password"))
                .roles("USER")
                .build());
        manager.createUser(User.withUsername("admin")
                .password(passwordEncoder.encode("Password1"))
                .roles("USER", "ADMIN")
                .build());
        return manager;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                //.csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"))
                .csrf().disable()
                .cors().disable()
                .exceptionHandling().and()
                .authorizeRequests(auth -> auth
                                .requestMatchers("/h2-console/**").hasRole("ADMIN")
                                .requestMatchers("/artists").authenticated()
                                .requestMatchers("/audioalbums").authenticated()
                                .requestMatchers("/audiotracks").authenticated()
                                //.requestMatchers("/h2-console").hasRole("ADMIN")
                                .requestMatchers("/lyrics").authenticated()
                                .requestMatchers("/login").permitAll()
                                .requestMatchers("/main/**").authenticated()
                        //.anyRequest().authenticated()
                )
                .userDetailsService(userDetailsService)
                //.authorizeRequests(auth -> auth
                //        .requestMatchers("/h2-console/**").permitAll()
                //        .requestMatchers("/main/**").permitAll()
                //        .anyRequest().authenticated()
                //)
                .headers(headers -> headers.frameOptions().sameOrigin())
                .httpBasic(withDefaults())
                .build();
    }
}
