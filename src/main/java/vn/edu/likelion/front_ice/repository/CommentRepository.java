package vn.edu.likelion.front_ice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.edu.likelion.front_ice.entity.ChallengerEntity;
import vn.edu.likelion.front_ice.entity.CommentEntity;

import java.util.Optional;

/**
 * CommentRepository -
 *
 * @param
 * @return
 * @throws
 */
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    Optional<CommentEntity> findByParentId(Long parentId);


    @Query("SELECT MAX(c.right) FROM CommentEntity c WHERE c.solution.id = :solutionId")
    Integer findMaxRightBySolutionId(@Param("solutionId") Long solutionId);

    @Modifying
    @Query("UPDATE CommentEntity c SET c.right = c.right + 2 WHERE c.right >= :startRight")
    void incrementRightValues(@Param("startRight") Integer startRight);

    @Modifying
    @Query("UPDATE CommentEntity c SET c.left = c.left + 2 WHERE c.left > :startRight")
    void incrementLeftValues(@Param("startRight") Integer startRight);

}
