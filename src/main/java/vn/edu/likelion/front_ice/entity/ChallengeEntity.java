package vn.edu.likelion.front_ice.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.SQLRestriction;
import vn.edu.likelion.front_ice.common.constants.SQLRestrictions;
import vn.edu.likelion.front_ice.common.enums.StatusChallenge;
import vn.edu.likelion.front_ice.common.enums.TypeChallenge;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * ChallengeEntity -
 *
 * @param
 * @return
 * @throws
 */
@Entity
@Table(name = "tbl_challenge")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@SQLRestriction(SQLRestrictions.SQL_DELETE_CONDITION)
public class ChallengeEntity extends BaseEntity {

    @Column(name = "title", length = 250, nullable = false)
    String title;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    CategoryEntity category;

    @ManyToMany
    @JoinTable(
            name = "tbl_challenge_technical",
            joinColumns = @JoinColumn(name = "challenge_id"),
            inverseJoinColumns = @JoinColumn(name = "technical_id")
    )
    Set<TechnicalEntity> technicals;

    
    @ManyToOne
    @JoinColumn(name = "challenge_point_id", nullable = false)
    ChallengePointEntity challengePoint;

    @OneToOne(mappedBy = "challenge",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            optional = false)
    ResourceEntity resource;

    @OneToMany(mappedBy = "challenge",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    Set<PreviewEntity> previews;

    @OneToMany(mappedBy = "challenge",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    Set<AccessChallengeEntity> accessChallenges;

    @Column(name = "open_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    LocalDateTime openDate;

    @Column(name = "close_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    LocalDateTime closeDate;

    @Column(name = "is_premium", nullable = false)
    boolean isPremium;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    StatusChallenge statusChallenge;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_challenge", nullable = false)
    TypeChallenge typeChallenge;

    @Column(name = "price", nullable = false)
    Double price;

    @Column(name = "message", nullable = false, columnDefinition = "TEXT")
    String message;

    @Column(name = "banner", nullable = false, columnDefinition = "TEXT")
    String banner;

    @Column(name = "is_hidden", nullable = false)
    boolean hidden;

    @Column(name = "assets", columnDefinition = "TEXT")
    String assets;

    @Column(name = "brief", columnDefinition = "TEXT")
    String brief;
}