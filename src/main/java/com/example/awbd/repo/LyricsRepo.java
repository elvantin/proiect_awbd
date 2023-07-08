package com.example.awbd.repo;

import com.example.awbd.model.Lyrics;
import com.example.awbd.model.security.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LyricsRepo extends JpaRepository<Lyrics, Long> {
    Optional<Lyrics> findByIdAudiotrack(Long id_audiotrack);
}
