package vn.edu.likelion.front_ice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.edu.likelion.front_ice.entity.ChallengeEntity;

import java.util.Optional;

@Repository
public interface ChallengeRepository extends JpaRepository<ChallengeEntity, Long>, JpaSpecificationExecutor<ChallengeEntity> {
    Page<ChallengeEntity> findByCategoryId(Long id, Pageable pageable);

    Optional<ChallengeEntity> findById(Long id);

    int countByChallengeCodeStartingWith(String prefix); // dùng để thêm set ChallengeCode khi tạo challenge

    @EntityGraph(attributePaths = {"category", "challengePoint", "technicals", "resource", "previews"})
    @Query("SELECT c FROM ChallengeEntity c ORDER BY c.createAt DESC")
    Page<ChallengeEntity> findAllChallenges(Pageable pageable);

    @Override
    @EntityGraph(attributePaths = {"category", "challengePoint", "technicals", "resource", "previews"})
    Page<ChallengeEntity> findAll(Specification<ChallengeEntity> spec, Pageable pageable);

    @EntityGraph(attributePaths = {"category", "challengePoint", "resource", "technicals", "previews"})
    @Query("SELECT c FROM ChallengeEntity c WHERE c.id = :challengeId")
    Optional<ChallengeEntity> findChallengeWithDetails(@Param("challengeId") Long challengeId);
}
