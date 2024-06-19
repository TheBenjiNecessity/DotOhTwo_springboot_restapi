package com.dotohtwo.readapi.repository;
 
import com.dotohtwo.readapi.repository.DAO.AppUserDAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUserDAO, Long> {
    Optional<AppUserDAO> findByUsername(String username);

    @Query(
        value = """
            SELECT u.*
            FROM app_user u
            WHERE u.username
                LIKE CONCAT('%', :text, '%')
            LIMIT :limit
            OFFSET :offset
        """,
        nativeQuery = true
    )
    Collection<AppUserDAO> findUsersBySearch(
        @Param("text") String searchText,
        @Param("limit") Integer limit,
        @Param("offset") Integer offset
    ); // TODO performance? Use elastic search
}