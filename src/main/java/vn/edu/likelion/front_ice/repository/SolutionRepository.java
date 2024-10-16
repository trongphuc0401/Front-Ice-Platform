package vn.edu.likelion.front_ice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.likelion.front_ice.entity.SolutionEntity;

@Repository
public interface SolutionRepository extends JpaRepository<SolutionEntity, String> {
//    Optional<LevelEntity> findById(String id);
}
