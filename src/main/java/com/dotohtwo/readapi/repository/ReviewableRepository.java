package com.dotohtwo.readapi.repository;

import com.dotohtwo.readapi.repository.DAO.ReviewableDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface ReviewableRepository extends JpaRepository<ReviewableDAO, Long> {
    @Query(
        value = """
            SELECT r.*
            FROM reviewable r
            WHERE r.info -> 'title' ->> :locale
                LIKE CONCAT('%', :text, '%')
            LIMIT :limit
            OFFSET :offset
        """,
        nativeQuery = true
    )
    Collection<ReviewableDAO> findReviewablesByTitle(
        @Param("text") String searchText,
        @Param("locale") String locale,
        @Param("limit") Integer limit,
        @Param("offset") Integer offset
    ); // TODO performance?
}