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
@Profile("h2")
public class DataLoader implements CommandLineRunner {

    private final AuthorityRepository authorityRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataLoader(AuthorityRepository authorityRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.authorityRepository = authorityRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private void loadUserData() {
        if (userRepository.count() == 0) {
            Authority adminRole = authorityRepository.findByRole("ROLE_ADMIN")
                    .orElseGet(() -> authorityRepository.save(Authority.builder().role("ROLE_ADMIN").build()));

            Authority guestRole = authorityRepository.findByRole("ROLE_GUEST")
                    .orElseGet(() -> authorityRepository.save(Authority.builder().role("ROLE_GUEST").build()));

            User admin = User.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("Password1"))
                    .authority(null)
                    .build();

            User guest = User.builder()
                    .username("guest")
                    .password(passwordEncoder.encode("password"))
                    .authority(null)
                    .build();

            admin = userRepository.save(admin);
            admin.setAuthority(adminRole);
            admin = userRepository.save(admin);

            guest = userRepository.save(guest);
            guest.setAuthority(guestRole);
            guest = userRepository.save(guest);
        }
    }

    @Override
    public void run(String... args) throws Exception {
        loadUserData();

       /* List<Persoane> persoane = persoaneRepo.findAll();

        for (Persoane persoana : persoane) {
            String username = persoana.getUzr();
            String password = persoana.getParola();
            Authority role;

            if ("elvantin".equals(username)) {
                role = authorityRepository.findByRole("ROLE_ADMIN").orElseGet(() -> authorityRepository.save(Authority.builder().role("ROLE_ADMIN").build()));
                persoana.setRol("ROLE_ADMIN");
            } else {
                role = authorityRepository.findByRole("ROLE_GUEST").orElseGet(() -> authorityRepository.save(Authority.builder().role("ROLE_GUEST").build()));
                persoana.setRol("ROLE_GUEST");
            }

            User user = User.builder()
                    .username(username)
                    .password(passwordEncoder.encode(password))
                    .authority(role)
                    .build();

            userRepository.save(user);
            persoaneRepo.save(persoana);
        }*/
    }


}
