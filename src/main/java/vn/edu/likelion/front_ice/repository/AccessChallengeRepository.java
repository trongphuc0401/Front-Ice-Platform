package vn.edu.likelion.front_ice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.likelion.front_ice.common.enums.ChallengeAccessStatus;
import vn.edu.likelion.front_ice.entity.AccessChallengeEntity;
import vn.edu.likelion.front_ice.entity.ChallengeEntity;
import vn.edu.likelion.front_ice.entity.ChallengerEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccessChallengeRepository extends JpaRepository<AccessChallengeEntity, String> {
    List<AccessChallengeEntity> findByChallengerAndStatus(ChallengerEntity challenger, ChallengeAccessStatus status);

    Optional<AccessChallengeEntity> findByChallengerAndChallenge(ChallengerEntity challengerEntity, ChallengeEntity challengeEntity);
}
