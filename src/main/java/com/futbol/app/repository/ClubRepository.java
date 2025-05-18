package com.futbol.app.repository;

import com.futbol.app.model.Club;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubRepository extends JpaRepository<Club, Long> {}
