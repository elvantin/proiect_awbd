package com.example.awbd.repo;

import com.example.awbd.model.Utilizator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtilizatorRepo extends JpaRepository<Utilizator, Long> {
}
