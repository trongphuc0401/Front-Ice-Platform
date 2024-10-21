package vn.edu.likelion.front_ice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.likelion.front_ice.entity.SolutionEntity;

import java.util.Optional;

@Repository
public interface SolutionRepository extends JpaRepository<SolutionEntity, String> {
//    Optional<SolutionEntity> findByChallengeIdAndChallengerId(String challengeId, String challengerId);
//    Optional<LevelEntity> findById(String id);
}
