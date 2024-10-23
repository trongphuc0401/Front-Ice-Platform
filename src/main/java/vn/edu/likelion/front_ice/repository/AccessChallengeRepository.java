package vn.edu.likelion.front_ice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.likelion.front_ice.common.enums.ChallengeAccessStatus;
import vn.edu.likelion.front_ice.entity.AccessChallengeEntity;

import java.util.List;

@Repository
public interface AccessChallengeRepository extends JpaRepository<AccessChallengeEntity, String> {
    List<AccessChallengeEntity> findByChallengerIdAndStatus(String id, ChallengeAccessStatus challengeAccessStatus);

}
