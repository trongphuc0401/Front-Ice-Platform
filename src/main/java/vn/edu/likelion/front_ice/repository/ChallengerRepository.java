package vn.edu.likelion.front_ice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.likelion.front_ice.entity.AccountEntity;
import vn.edu.likelion.front_ice.entity.ChallengerEntity;

import java.util.Optional;

public interface ChallengerRepository extends JpaRepository<ChallengerEntity, String> {
    Optional<ChallengerEntity> findByAccountId(String id);
}
