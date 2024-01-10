package com.dotohtwo.readapi.repository;
 
import com.dotohtwo.readapi.model.Reviewable;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewableRepository extends JpaRepository<Reviewable, Long> {}