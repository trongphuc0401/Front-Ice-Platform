package vn.edu.likelion.front_ice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.likelion.front_ice.entity.LevelEntity;
import vn.edu.likelion.front_ice.entity.StaffEntity;

import java.util.Optional;

@Repository
public interface LevelRepository extends JpaRepository<LevelEntity, String> {
//    Optional<LevelEntity> findById(String id);
}
