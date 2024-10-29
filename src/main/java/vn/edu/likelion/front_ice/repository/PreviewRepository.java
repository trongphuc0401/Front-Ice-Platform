package vn.edu.likelion.front_ice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.likelion.front_ice.entity.ChallengeEntity;
import vn.edu.likelion.front_ice.entity.ChallengerEntity;
import vn.edu.likelion.front_ice.entity.LevelEntity;
import vn.edu.likelion.front_ice.entity.PreviewEntity;

import java.util.Optional;

/**
 * PreviewRepository -
 *
 * @param
 * @return
 * @throws
 */

@Repository
public interface PreviewRepository extends JpaRepository<PreviewEntity, String> {
    Optional<PreviewEntity> findByChallengeAndLabel(ChallengeEntity challenge, String label);
}
