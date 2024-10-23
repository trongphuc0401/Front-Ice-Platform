package vn.edu.likelion.front_ice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.SQLRestriction;
import vn.edu.likelion.front_ice.common.constants.SQLRestrictions;


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

    @Column
    String title;

    @Column
    int minScore;

    @Column
    int maxScore;

    @Column
    String nextLevelId;
}

