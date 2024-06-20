package com.dotohtwo.readapi.repository;

import com.dotohtwo.readapi.repository.DAO.ReviewDAO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<ReviewDAO, Long> {}