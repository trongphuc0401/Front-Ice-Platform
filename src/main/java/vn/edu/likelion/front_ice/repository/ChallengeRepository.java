package vn.edu.likelion.front_ice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.edu.likelion.front_ice.entity.ChallengeEntity;

import java.util.Optional;

@Repository
public interface ChallengeRepository extends JpaRepository<ChallengeEntity, String> {
    //    Optional<ChallengeEntity> findByAccountId(String id);

    //    // Query native reference
//    @Query(value = "SELECT tp.id,tp.name FROM tbl_plant tp" +
//            " WHERE LOWER(tp.name) LIKE LOWER(CONCAT(:searchText, '%')) AND tp.isDeleted = 0", nativeQuery = true)
//    Page<Object[]> findPlantBySearchText1(@Param("searchText") String searchText, Pageable pageable);
//
//    @Query("SELECT p FROM ChallengeEntity p WHERE p.isDeleted = 0")
    Page<ChallengeEntity> findByCategoryId(String id, Pageable pageable);v
}
