package vn.edu.likelion.front_ice.repository;

import jakarta.persistence.Cacheable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.edu.likelion.front_ice.common.enums.Level;
import vn.edu.likelion.front_ice.entity.LevelEntity;
import vn.edu.likelion.front_ice.entity.StaffEntity;

import java.util.Optional;

@Repository
public interface LevelRepository extends JpaRepository<LevelEntity, Long> {
    LevelEntity findByLevel(Level level);


    Optional<LevelEntity> findById(Long id);

}
