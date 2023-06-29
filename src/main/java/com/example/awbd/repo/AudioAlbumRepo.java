package com.example.awbd.repo;

import com.example.awbd.model.AudioAlbum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AudioAlbumRepo extends JpaRepository<AudioAlbum, Long> {
}
