package vn.edu.likelion.front_ice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;


/**
 * TechnicalEntity -
 *
 * @param
 * @return
 * @throws
 */
@Entity
@Table(name = "tbl_technical")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TechnicalEntity extends BaseEntity {

    @Column(nullable = false)
    String title;

    @ManyToMany(mappedBy = "technicals")
    private Set<ChallengeEntity> challenges;
}

