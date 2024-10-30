package vn.edu.likelion.front_ice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.edu.likelion.front_ice.entity.AccountEntity;
import vn.edu.likelion.front_ice.entity.ChallengerEntity;

import java.util.Optional;

@Repository
public interface ChallengerRepository extends JpaRepository<ChallengerEntity, Long> {


    Optional<ChallengerEntity> findByAccountId(Long id);

//    @Query(value = "SELECT * FROM getchallengerbyemail(?1) LIMIT 1", nativeQuery = true)
//    @Query(value = "SELECT * FROM "tbl_challenger" , nativeQuery = true)
//    Optional<ChallengerEntity> findChallengerByEmail(String email);
    Optional<ChallengerEntity> findByAccountEmail(String email);
}
