package vn.edu.likelion.front_ice.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.SQLRestriction;
import vn.edu.likelion.front_ice.common.constants.SQLRestrictions;
import vn.edu.likelion.front_ice.common.enums.Level;

import java.util.Set;


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
public class LevelEntity extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    Level level;

    @Column
    int minScore;

    @Column
    int maxScore;

    @Column
    String nextLevelId;

    @OneToMany(mappedBy = "level",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    Set<ChallengerEntity> challengers;
}

