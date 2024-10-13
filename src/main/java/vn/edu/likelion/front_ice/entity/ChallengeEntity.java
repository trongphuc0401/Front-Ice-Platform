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

    @Column(name = "category_id", nullable = false) // chưa nối bảng khi làm rồi hãy nối
    String categoryId;

    @Column(name = "technical_id", nullable = false) // chưa nối bảng khi làm ròi hãy nối
    String technicalId;

    @Column(name = "challenge_point_id", nullable = false)  // chưa nối bảng khi làm ròi hãy nối
    String challengePointId;

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
    StatusChallenge status;

    @Column(name = "price", nullable = false)
    Double price;

    @Column(name = "message", nullable = false, columnDefinition = "TEXT")
    String message;

    @Column(name = "banner", nullable = false, columnDefinition = "TEXT")
    String banner;

    @Column(name = "is_hidden", nullable = false)
    boolean isHidden;

    @Column(name = "mobile_design_image", columnDefinition = "TEXT NOT NULL")
    String mobileDesignImage;

    @Column(name = "tablet_design_image", columnDefinition = "TEXT NOT NULL")
    String tabletDesignImage;

    @Column(name = "desktop_design_image", columnDefinition = "TEXT NOT NULL")
    String desktopDesignImage;
}