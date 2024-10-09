package vn.edu.likelion.front_ice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.likelion.front_ice.entity.ChallengeEntity;

import java.util.Optional;

@Repository
public interface ChallengeRepository extends JpaRepository<ChallengeEntity, String> {



}
