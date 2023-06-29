package com.example.awbd.repo;

import com.example.awbd.model.Lyrics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LyricsRepo extends JpaRepository<Lyrics, Long> {
}
