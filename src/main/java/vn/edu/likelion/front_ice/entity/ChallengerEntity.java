package vn.edu.likelion.front_ice.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.SQLRestriction;
import vn.edu.likelion.front_ice.common.constants.SQLRestrictions;

import java.util.Set;


/**
 * ChallengerEntity -
 *
 * @param
 * @return
 * @throws
 */
@Entity
@Table(name = "tbl_challenger")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@SQLRestriction(SQLRestrictions.SQL_DELETE_CONDITION)
public class ChallengerEntity extends BaseEntity {

    @Column
    String accountId;

    @Column
    String levelId;

    @Column(name = "is_premium", nullable = false, unique = false)
    int isPremium;

    @Column
    String urlGithub;

    @Column
    int score;

    @Column(name = "url_cv")
    String urlCV;

    @Column(columnDefinition = "TEXT")
    String urlPortfolio;

    @Column(columnDefinition = "TEXT")
    String urlCodepen;

    @Column(columnDefinition = "TEXT")
    String urlGitLab;

    @Column(columnDefinition = "TEXT")
    String urlStackOverflow;

    @Column(columnDefinition = "TEXT")
    String urlLinkedIn;

    @Column
    int totalJoinedChallenge;

    @Column
    int totalSubmittedChallenge;

    @OneToMany(mappedBy = "challenger",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    Set<AccessChallengeEntity> accessChallenges;
}
