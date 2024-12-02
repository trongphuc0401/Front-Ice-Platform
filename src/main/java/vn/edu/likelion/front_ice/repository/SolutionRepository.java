package vn.edu.likelion.front_ice.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.edu.likelion.front_ice.dto.response.challenge.ParticipationSubmissionCount;
import vn.edu.likelion.front_ice.dto.response.solution.OtherSolutionResponse;
import vn.edu.likelion.front_ice.dto.response.solution.SolutionResponse;
import vn.edu.likelion.front_ice.entity.ChallengerEntity;
import vn.edu.likelion.front_ice.entity.SolutionEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface SolutionRepository extends JpaRepository<SolutionEntity, Long> {
    /**
     * Counts the number of solutions with codes starting with the specified prefix.
     *
     * @param prefix the prefix to search for in solution codes
     * @return the count of solutions with codes starting with the given prefix
     */
    int countBySolutionCodeStartingWith(String prefix);

    /**
     * Retrieves solutions associated with a specific challenger where the 'isSubmitted' flag matches the provided value.
     *
     * @param challenger the challenger entity to filter solutions
     * @param b the 'isSubmitted' flag value to match
     * @return a list of matching SolutionEntity objects
     */
    @Query(value = "select s from SolutionEntity s where s.challenger = :challenger and s.isJoined = :b")
    List<SolutionEntity> findByChallengerAndIsJoined(@Param("challenger") ChallengerEntity challenger,
                                                     @Param("b") boolean b);

    /**
     * Retrieves solutions associated with a specific challenger where the 'isSubmitted' flag matches the provided value.
     *
     * @param challenger the challenger entity to filter solutions
     * @param b the 'isSubmitted' flag value to match
     * @return a list of matching SolutionEntity objects
     */
    @Query(value = "select s from SolutionEntity s where s.challenger = :challenger and s.isSubmitted = :b")
    List<SolutionEntity> findByChallengerAndIsSubmitted(@Param("challenger") ChallengerEntity challenger,
                                                        @Param("b") boolean b);

    /**
     * Finds a solution by challenge ID, challenger ID, and the 'isJoined' flag value.
     *
     * @param challengeId the ID of the challenge
     * @param id the ID of the challenger
     * @param b the 'isJoined' flag value to match
     * @return an Optional containing the matching SolutionEntity, if found
     */
    @Query(value = "select s from SolutionEntity s where s.challenge.id = :challengeId" +
            " and s.challenger.id = :id and s.isJoined = :b")
    Optional<SolutionEntity> findByChallengeIdAndChallengerIdAndIsJoined(@Param("challengeId") Long challengeId,
                                                                         @Param("id") Long id,
                                                                         @Param("b") boolean b);
    /**
     * Finds a solution based on challenger ID and challenge ID.
     *
     * @param challengerId the ID of the challenger
     * @param challengeId the ID of the challenge
     * @return an Optional containing the matching SolutionEntity, if found
     */
    Optional<SolutionEntity> findByChallenger_IdAndChallenge_Id(Long challengerId, Long challengeId);

    /**
     * Counts the number of joined and submitted solutions for a given challenge.
     *
     * @param challengeId the ID of the challenge
     * @return a ParticipationSubmissionCount DTO containing the counts of joined and submitted solutions
     */
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

    /**
     * Retrieves all submitted solutions for a given challenge, mapped to a simplified response object.
     *
     * @param challengeId the ID of the challenge
     * @return a list of OtherSolutionResponse DTOs for the matching solutions
     */
    @Query("""
    SELECT new vn.edu.likelion.front_ice.dto.response.solution.OtherSolutionResponse(
        s.id,
        s.title,
        s.description,
        s.urlProduct,
        s.urlRepository,
        s.note,
        c.account.firstName,
        c.account.lastName,
        l.level,
        c.account.avatar
    )
    FROM SolutionEntity s
    JOIN s.challenger c
    JOIN LevelEntity l ON c.levelId = l.id
    WHERE s.challenge.id = :challengeId AND s.isSubmitted = true
    """)
    List<OtherSolutionResponse> findOtherSolutions(@Param("challengeId") Long challengeId);
}
