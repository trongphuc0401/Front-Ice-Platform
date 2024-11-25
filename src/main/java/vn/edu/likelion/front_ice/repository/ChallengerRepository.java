package vn.edu.likelion.front_ice.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.likelion.front_ice.entity.AccountEntity;
import vn.edu.likelion.front_ice.entity.ChallengeEntity;
import vn.edu.likelion.front_ice.entity.ChallengerEntity;
import vn.edu.likelion.front_ice.projection.challenger.IsPremiumProjection;

import java.util.Optional;

@Repository
public interface ChallengerRepository extends JpaRepository<ChallengerEntity, Long> {


    Optional<ChallengerEntity> findByAccountId(Long id);

//    @Query(value = "SELECT * FROM getchallengerbyemail(?1) LIMIT 1", nativeQuery = true)
//    @Query(value = "SELECT * FROM "tbl_challenger" , nativeQuery = true)
//    Optional<ChallengerEntity> findChallengerByEmail(String email);

    @Query("select a.challenger from AccountEntity a where a.email = :email")
    Optional<ChallengerEntity> findByAccountEmail(@Param("email") String email);

    Optional<ChallengerEntity> findByAccount_Id( Long accountId);

    @Query("SELECT ch.isPremium " +
            "FROM AccountEntity a " +
            "join ChallengerEntity ch on a.id = ch.account.id "+
            "WHERE a.email = :email")
    Optional<Boolean> findIsPremiumProjectionByAccountEmail(@Param("email") String email);

    // @Modifying
    // @Transactional
    // @Query("UPDATE ChallengerEntity c SET c.levelId = :levelId WHERE c.id = :id")
    // void updateLevelId(@Param("id") Long id, @Param("levelId") Long levelId);

    @Modifying
    @Query(value = "CALL update_challenger_level(:challengerId, :newLevelId)", nativeQuery = true)
    void updateLevelId(@Param("challengerId") Long id, @Param("newLevelId") Long levelId);
}


