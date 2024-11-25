package vn.edu.likelion.front_ice.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.SQLRestriction;
import vn.edu.likelion.front_ice.common.constants.SQLRestrictions;
import vn.edu.likelion.front_ice.common.enums.Level;


/**
 * LevelEntity -
 *
 * @param
 * @return
 * @throws
 */
@Entity
@Table(name = "tbl_level")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@SQLRestriction(SQLRestrictions.SQL_DELETE_CONDITION)
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class LevelEntity extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    Level level;

    @Column
    int minScore;

    @Column
    int maxScore;

    @Column
    Long nextLevelId;
}

