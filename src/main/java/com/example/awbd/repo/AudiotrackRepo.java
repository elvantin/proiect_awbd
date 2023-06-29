package com.example.awbd.repo;

import com.example.awbd.model.Audiotrack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AudiotrackRepo extends JpaRepository<Audiotrack, Long> {
}
