package vn.edu.likelion.front_ice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.likelion.front_ice.entity.StaffEntity;

import java.util.Optional;

@Repository
public interface StaffRepository extends JpaRepository<StaffEntity, String> {
    Optional<StaffEntity> findByAccountId(String id);
}
