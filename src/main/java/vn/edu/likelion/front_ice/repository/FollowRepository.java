package vn.edu.likelion.front_ice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.likelion.front_ice.entity.AccountEntity;
import vn.edu.likelion.front_ice.entity.FollowEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface FollowRepository extends JpaRepository<FollowEntity, Long> {
    Optional<List<FollowEntity>> findByChallengerId(Long challengerId);
    Optional<List<FollowEntity>> findByRecruiterId(Long recruiterId);

    Optional<FollowEntity> findByChallengerIdAndRecruiterId(Long challengerId, Long recruiterId);
}
