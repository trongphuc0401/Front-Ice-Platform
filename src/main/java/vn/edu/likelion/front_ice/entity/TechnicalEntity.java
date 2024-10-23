package vn.edu.likelion.front_ice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.SQLRestriction;
import vn.edu.likelion.front_ice.common.constants.SQLRestrictions;

import java.util.List;
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
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@SQLRestriction(SQLRestrictions.SQL_DELETE_CONDITION)
public class TechnicalEntity extends BaseEntity {

    @Column(nullable = false)
    String title;

    @ManyToMany(mappedBy = "technicals")
    Set<ChallengeEntity> challenges;
}

