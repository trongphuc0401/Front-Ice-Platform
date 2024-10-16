package vn.edu.likelion.front_ice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
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

@Repository
public interface ResourceRepository extends JpaRepository<ResourceEntity, String> {

    @Query(value = "SELECT * FROM GetResourceByChallengeId(?1) LIMIT 1", nativeQuery = true)
    Optional<ResourceEntity> findByChallengeId(String id);
}
