package vn.edu.likelion.front_ice.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SQLRestriction;
import vn.edu.likelion.front_ice.common.constants.SQLRestrictions;
import vn.edu.likelion.front_ice.common.enums.StatusSolution;

import java.util.Set;


/**
 * SolutionEntity -
 *
 * @param
 * @return
 * @throws
 */
@Entity
@Table(name = "tbl_solution")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@SQLRestriction(SQLRestrictions.SQL_DELETE_CONDITION)
public class SolutionEntity extends BaseEntity {

    @Column
    String urlProduct;

    @Column
    String urlRepository;

    @Column
    String title;

    @Column
    String description;

    @Column
    String note;

    @Column(name = "is_hidden")
    @ColumnDefault("false")
    boolean hidden;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_solution")
    StatusSolution statusSolution;

    @OneToMany(mappedBy = "solution",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    Set<AccessChallengeEntity> accessChallenges;
}

