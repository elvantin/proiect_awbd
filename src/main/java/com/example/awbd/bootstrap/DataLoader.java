package com.example.awbd.bootstrap;

import com.example.awbd.model.security.Authority;
import com.example.awbd.model.security.User;
import com.example.awbd.repo.security.AuthorityRepository;
import com.example.awbd.repo.security.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile("mysql")
public class DataLoader implements CommandLineRunner {

    private final AuthorityRepository authorityRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataLoader(AuthorityRepository authorityRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.authorityRepository = authorityRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // incarca datele utilizatorilor si ia actiune daca repo-ul e gol
    private void loadUserData() {
        if (userRepository.count() == 0) {
            // vezi daca exista ROLE_ADMIN in authority repository, daca nu, il cream.
            Authority adminRole = authorityRepository.findByRole("ROLE_ADMIN")
                    .orElseGet(() -> authorityRepository.save(Authority.builder().role("ROLE_ADMIN").build()));

            // la fel pentru ROLE_GUEST
            Authority guestRole = authorityRepository.findByRole("ROLE_GUEST")
                    .orElseGet(() -> authorityRepository.save(Authority.builder().role("ROLE_GUEST").build()));

        }
    }
    @Override
    public void run(String... args) throws Exception {
        // apeleaza metoda la pornire
        loadUserData();
    }
}
