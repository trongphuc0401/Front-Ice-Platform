package vn.edu.likelion.front_ice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.edu.likelion.front_ice.entity.TechnicalEntity;

import java.util.Set;

@Repository
public interface TechnicalRepository extends JpaRepository<TechnicalEntity, Long> {

}
