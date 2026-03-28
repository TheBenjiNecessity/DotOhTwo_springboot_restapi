package com.dotohtwo.readapi.repository;

import com.dotohtwo.readapi.repository.DAO.FollowDAO;
import com.dotohtwo.readapi.repository.DAO.FollowId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface FollowRepository extends JpaRepository<FollowDAO, FollowId> {

    @Query(
        value = """
            SELECT u.username
            FROM follow f
            JOIN app_user u ON u.id = f.follower_id
            WHERE f.followed_id = :userId
        """,
        nativeQuery = true
    )
    List<String> findFollowerUsernamesByUserId(@Param("userId") UUID userId);
}
