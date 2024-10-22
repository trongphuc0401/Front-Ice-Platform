package vn.edu.likelion.front_ice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.likelion.front_ice.common.enums.ChallengeAccessStatus;
import vn.edu.likelion.front_ice.entity.AccessChallenge;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccessChallengeRepository extends JpaRepository<AccessChallenge, String> {
    List<AccessChallenge> findByChallengerIdAndStatus(String id, ChallengeAccessStatus challengeAccessStatus);

}
