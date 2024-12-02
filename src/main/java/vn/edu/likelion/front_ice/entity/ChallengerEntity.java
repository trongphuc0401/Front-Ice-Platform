package vn.edu.likelion.front_ice.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SQLRestriction;
import vn.edu.likelion.front_ice.common.constants.SQLRestrictions;
import vn.edu.likelion.front_ice.common.enums.Gender;

import java.sql.Timestamp;
import java.time.LocalDateTime;
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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    AccountEntity account;

    @Column
    Long levelId;

    @Column(name = "is_premium", nullable = false)
    @ColumnDefault("false")
    boolean isPremium;

    @Column
    String urlGithub;

    @Column
    int score;

    @Column(columnDefinition = "TEXT")
    String bio;

    @Column
    LocalDateTime birthday;

    @Column(name = "gender",nullable = true)
    Gender gender;

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
    Set<SolutionEntity> solutions;
}
