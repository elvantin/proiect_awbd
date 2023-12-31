package com.example.awbd.services.security;

import com.example.awbd.model.security.Authority;
import com.example.awbd.model.security.User;
import com.example.awbd.repo.security.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Profile("mysql")
public class JpaUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User user;

            // ia utilizatorii din repo dupa username
            Optional<User> userOpt = userRepository.findByUsername(username);
            if (userOpt.isPresent())
                user = userOpt.get();
            else
                throw new UsernameNotFoundException("Username: " + username);

            // logare informatii user
            log.info(user.toString());

            Set<Authority> authorities = new HashSet<>();
            authorities.add(user.getAuthority());

            // creare&afisare obiect UserDetails cu informatii despre user +autorizari
            return new org.springframework.security.core.userdetails.User(user.getUsername(),
                    user.getPassword(), user.getEnabled(), user.getAccountNotExpired(),
                    user.getCredentialsNotExpired(), user.getAccountNotLocked(), getAuthorities(authorities));
        }
        catch (UsernameNotFoundException unf){
            // logare exceptie daca nu gaseste user si aruncarea ei
            log.error(unf.toString());
            throw unf;
        }
        catch (Exception ex){
            // logare exceptie si aruncarea ei
            log.error(ex.toString());
            throw ex;
        }
    }

    // mapare autorizari la obiectele  din GrantedAuthority
    private Collection<? extends GrantedAuthority> getAuthorities(Set<Authority> authorities) {
        if (authorities == null) {
            return new HashSet<>();
        } else if (authorities.size() == 0) {
            return new HashSet<>();
        } else {
            return authorities.stream()
                    .map(Authority::getRole)
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toSet());
        }
    }
}