package vn.edu.likelion.front_ice.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.edu.likelion.front_ice.entity.AccountEntity;
import vn.edu.likelion.front_ice.projection.challenger.OverviewProjection;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {

    Optional<AccountEntity> findByEmail(String email);

    @Query(value = "select a from AccountEntity a JOIN FETCH a.challenger c where a.email = :email ")
    Optional<AccountEntity> findChallengerByEmail(@Param("email") String email);

    Optional<AccountEntity> findByEmailAndRefreshToken(String email, String refreshToken);

    @EntityGraph(attributePaths = {"challenger", "recruiter", "staff"})
    @Query("SELECT a FROM AccountEntity a WHERE a.email = :email")
    Optional<AccountEntity> findByEmailWithDetails(@Param("email") String email);

    @Query("""
        SELECT 
            a.id AS id,
            a.firstName AS firstName, 
            a.lastName AS lastName, 
            a.avatar AS avatar, 
            a.email AS email, 
            c.isPremium AS isPremium, 
            c.score AS score 
        FROM AccountEntity a 
        JOIN a.challenger c 
        WHERE a.email = :email AND a.isDeleted = 0 AND c.isDeleted = 0
    """)
    Optional<OverviewProjection> findOverviewByEmail(@Param("email") String email);
}
