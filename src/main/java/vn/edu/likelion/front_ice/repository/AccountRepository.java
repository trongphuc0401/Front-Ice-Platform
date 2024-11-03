package vn.edu.likelion.front_ice.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.edu.likelion.front_ice.entity.AccountEntity;

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
}
