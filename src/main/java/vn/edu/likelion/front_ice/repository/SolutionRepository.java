package vn.edu.likelion.front_ice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.edu.likelion.front_ice.dto.response.challenge.ParticipationSubmissionCount;
import vn.edu.likelion.front_ice.entity.ChallengerEntity;
import vn.edu.likelion.front_ice.entity.SolutionEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface SolutionRepository extends JpaRepository<SolutionEntity, Long> {
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

    Optional<SolutionEntity> findByChallenger_IdAndChallenge_Id(Long challengerId, Long challengeId);

    @Query("""
    SELECT
        new vn.edu.likelion.front_ice.dto.response.challenge.ParticipationSubmissionCount(
            COUNT(CASE WHEN s.isJoined = true THEN 1 ELSE null END),
            COUNT(CASE WHEN s.isSubmitted = true THEN 1 ELSE null END)
        )
    FROM SolutionEntity s
    WHERE s.challenge.id = :challengeId
    """)
    ParticipationSubmissionCount countParticipationAndSubmission(@Param("challengeId") Long challengeId);
}
