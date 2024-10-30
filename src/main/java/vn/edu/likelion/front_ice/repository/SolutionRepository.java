package vn.edu.likelion.front_ice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.likelion.front_ice.entity.ChallengerEntity;
import vn.edu.likelion.front_ice.entity.SolutionEntity;

import java.util.Collection;
import java.util.List;

@Repository
public interface SolutionRepository extends JpaRepository<SolutionEntity, Long> {
//    Optional<SolutionEntity> findByChallengeIdAndChallengerId(String challengeId, String challengerId);
//    Optional<LevelEntity> findById(String id);

    int countBySolutionCodeStartingWith(String prefix);

    List<SolutionEntity> findByChallengerAndJoined(ChallengerEntity challenger, boolean b);

    List<SolutionEntity> findByChallengerAndSubmitted(ChallengerEntity challenger, boolean b);
}
