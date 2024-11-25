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
import vn.edu.likelion.front_ice.projection.challenge.TypeChallengeProjection;

import java.util.Optional;

@Repository
public interface ChallengeRepository extends JpaRepository<ChallengeEntity, Long>, JpaSpecificationExecutor<ChallengeEntity> {
    Page<ChallengeEntity> findByCategoryId(Long id, Pageable pageable);

    Optional<ChallengeEntity> findById(Long id);

    int countByChallengeCodeStartingWith(String prefix); // dùng để thêm set ChallengeCode khi tạo challenge

    @EntityGraph(attributePaths = {"category", "challengePoint", "technicals", "resource", "previews"})
    @Query("SELECT c FROM ChallengeEntity c ORDER BY c.createAt DESC")
    Page<ChallengeEntity> findAllChallenges(Pageable pageable);

    @EntityGraph(attributePaths = {"category", "challengePoint", "technicals", "resource", "previews"})
    @Query("""
        SELECT c FROM ChallengeEntity c
        JOIN SolutionEntity s ON c.id = s.challenge.id
        JOIN ChallengerEntity c2 ON c2.id = s.challenger.id
        JOIN AccountEntity ta ON c2.account.id = ta.id
        WHERE s.isJoined = true
          AND c.isDeleted = 0
          AND ta.email = :email
    """)
    Page<ChallengeEntity> findAllJoinedChallenge(@Param("email") String email, Pageable pageable);

    // void findJoined

    @Override
    @EntityGraph(attributePaths = {"category", "challengePoint", "technicals", "resource", "previews"})
    Page<ChallengeEntity> findAll(Specification<ChallengeEntity> spec, Pageable pageable);

    @EntityGraph(attributePaths = {"category", "challengePoint", "resource", "technicals", "previews"})
    @Query("SELECT c FROM ChallengeEntity c WHERE c.id = :challengeId")
    Optional<ChallengeEntity> findChallengeWithDetails(@Param("challengeId") Long challengeId);

    @Query("SELECT ch.typeChallenge AS typeChallenge " +
            "FROM ChallengeEntity ch " +
            "JOIN ResourceEntity r ON r.challenge = ch " +
            "WHERE r.figmaId = :figmaId")
    Optional<TypeChallengeProjection> findTypeChallengeByFigmaId(@Param("figmaId") String figmaId);


}
