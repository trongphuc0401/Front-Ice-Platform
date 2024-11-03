package vn.edu.likelion.front_ice.repository;

import jakarta.persistence.Entity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.edu.likelion.front_ice.entity.ChallengerEntity;
import vn.edu.likelion.front_ice.entity.SolutionEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface SolutionRepository extends JpaRepository<SolutionEntity, Long> {
//    Optional<SolutionEntity> findByChallengeIdAndChallengerId(String challengeId, String challengerId);
//    Optional<LevelEntity> findById(String id);

    int countBySolutionCodeStartingWith(String prefix);

    @Query(value = "select s from SolutionEntity s where s.challenger = :challenger and s.isJoined = :b")
    List<SolutionEntity> findByChallengerAndIsJoined(@Param("challenger") ChallengerEntity challenger,
                                                     @Param("b") boolean b);

    @Query(value = "select s from SolutionEntity s where s.challenger = :challenger and s.isSubmitted = :b")
    List<SolutionEntity> findByChallengerAndIsSubmitted(@Param("challenger") ChallengerEntity challenger,
                                                        @Param("b") boolean b);

    @Query(value = "select s from SolutionEntity s where s.challenge.id = :challengeId" +
            " and s.challenger.id = :id and s.isJoined = :b")
    Optional<SolutionEntity> findByChallengeIdAndChallengerIdAndIsJoined(@Param("challengeId") Long challengeId,
                                                                         @Param("id") Long id,
                                                                         @Param("b") boolean b);

    @EntityGraph(attributePaths = {
            "technicals",
            "challengePoint"
    })
    Page<SolutionEntity> findAllByIsDeletedFalse(Pageable pageable);
}
