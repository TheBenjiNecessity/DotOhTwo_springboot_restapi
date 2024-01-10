package com.dotohtwo.readapi.repository;
 
import com.dotohtwo.readapi.model.Review;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {}