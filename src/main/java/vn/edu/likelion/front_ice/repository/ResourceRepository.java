package vn.edu.likelion.front_ice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.likelion.front_ice.entity.ResourceEntity;
import vn.edu.likelion.front_ice.projection.resource.AssetsNameProjection;
import vn.edu.likelion.front_ice.projection.resource.AssetsUrlProjection;
import vn.edu.likelion.front_ice.projection.resource.FigmaNameProjection;
import vn.edu.likelion.front_ice.projection.resource.FigmaUrlProjection;

import java.util.Optional;

/**
 * ResourceRepository -
 *
 * @param
 * @return
 * @throws
 */

@Repository
public interface ResourceRepository extends JpaRepository<ResourceEntity, Long> {


    // @Query(value = "SELECT * FROM GetResourceByChallengeId(?1) LIMIT 1", nativeQuery = true)
    Optional<ResourceEntity> findByChallengeId(Long id);

    Optional<ResourceEntity> findByAssetsId(String assetsUrl);

    Optional<AssetsUrlProjection> findAssetsUrlByAssetsId(String assetsId);

    Optional<FigmaUrlProjection> findFigmaUrlByFigmaId(String figmaId);

    Optional<AssetsNameProjection> findAssetsNameByAssetsId(String assetsId);

    Optional<FigmaNameProjection> findFigmaNameByFigmaId(String assetsId);





}
