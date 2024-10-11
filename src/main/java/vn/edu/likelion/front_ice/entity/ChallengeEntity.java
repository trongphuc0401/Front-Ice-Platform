package vn.edu.likelion.front_ice.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import vn.edu.likelion.front_ice.common.enums.StatusChallenge;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * ChallengeEntity -
 *
 * @param
 * @return
 * @throws
 */
@Entity
@Table(name = "tbl_challenge")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChallengeEntity extends BaseEntity {

    @Column(name = "title", length = 250, nullable = false)
    String title;

    @Column(name = "category_id", nullable = false)
    String categoryId;

    @Column(name = "technical_id", nullable = false)
    String technicalId;

    @Column(name = "level_id", nullable = false)
    String levelId;

    @Column(name = "open_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    LocalDateTime openDate;

    @Column(name = "close_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    LocalDateTime closeDate;

    @Column(name = "is_premium", nullable = false)
    int isPremium;

    @Column(name = "score", nullable = false)
    Integer score;

    @Column(name = "description", nullable = false)
    String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    StatusChallenge status;

    @Column(name = "price", nullable = false)
    Double price;

    @Column(name = "message", length = 50, nullable = false)
    String message;
}
