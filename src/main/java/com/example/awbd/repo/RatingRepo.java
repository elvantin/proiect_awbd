package com.example.awbd.repo;

import com.example.awbd.model.Persoane;
import com.example.awbd.model.Rating;
import com.example.awbd.model.RatingId;

import com.example.awbd.model.Audiotrack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RatingRepo extends JpaRepository<Rating, RatingId> {
    Optional<Rating> findByPersoaneAndAudiotrack(Persoane persoane, Audiotrack audiotrack);
}
