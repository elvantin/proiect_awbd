package com.example.awbd.bootstrap;

import com.example.awbd.model.Persoane;
import com.example.awbd.model.security.Authority;
import com.example.awbd.model.security.User;
import com.example.awbd.repo.PersoaneRepo;
import com.example.awbd.repo.security.AuthorityRepository;
import com.example.awbd.repo.security.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
@Profile("h2")
public class DataLoader implements CommandLineRunner {

    private final AuthorityRepository authorityRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PersoaneRepo persoaneRepo;
    private Authority adminRole;
    private Authority guestRole;

    private void loadUserData() {
        if (userRepository.count() == 0) {
            Authority adminRole = authorityRepository.findByRole("ROLE_ADMIN")
                    .orElseGet(() -> authorityRepository.save(Authority.builder().role("ROLE_ADMIN").build()));

            Authority guestRole = authorityRepository.findByRole("ROLE_GUEST")
                    .orElseGet(() -> authorityRepository.save(Authority.builder().role("ROLE_GUEST").build()));

            User admin = User.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("Password1"))
                    .authority(adminRole)
                    .build();

            User guest = User.builder()
                    .username("guest")
                    .password(passwordEncoder.encode("password"))
                    .authority(guestRole)
                    .build();

            userRepository.save(admin);
            userRepository.save(guest);
        }
    }


    @Override
    public void run(String... args) throws Exception {
        loadUserData();

        Authority adminRole = authorityRepository.findByRole("ROLE_ADMIN")
                .orElseThrow(() -> new RuntimeException("Admin role not found"));

        Authority guestRole = authorityRepository.findByRole("ROLE_GUEST")
                .orElseThrow(() -> new RuntimeException("Guest role not found"));

        List<Persoane> persoane = persoaneRepo.findAll();

        for (Persoane persoana : persoane) {
            String username = persoana.getUzr();
            String password = persoana.getParola();
            Authority role;

            if ("elvantin".equals(username)) {
                role = adminRole;
                persoana.setRol("ROLE_ADMIN");
            } else {
                role = guestRole;
                persoana.setRol("ROLE_GUEST");
            }

            User user = User.builder()
                    .username(username)
                    .password(passwordEncoder.encode(password))
                    .authority(role)
                    .build();

            userRepository.save(user);
            persoaneRepo.save(persoana);
        }
    }

}