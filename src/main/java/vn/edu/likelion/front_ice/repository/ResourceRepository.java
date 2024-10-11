package vn.edu.likelion.front_ice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.likelion.front_ice.entity.RecruiterEntity;
import vn.edu.likelion.front_ice.entity.ResourceEntity;
import vn.edu.likelion.front_ice.service.BaseService;

import java.util.Optional;

/**
 * ResourceRepository -
 *
 * @param
 * @return
 * @throws
 */
public interface ResourceRepository extends JpaRepository<ResourceEntity, String> {
    Optional<ResourceEntity> findByChallengeId(String id);
}
