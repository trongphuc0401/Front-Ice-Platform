package vn.edu.likelion.front_ice.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import vn.edu.likelion.front_ice.common.enums.ChallengeAccessStatus;

@Entity
@Table(name = "tbl_access_challenge")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccessChallenge extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ChallengeAccessStatus status;

    @Column(name = "message", columnDefinition = "TEXT")
    private String message;

    @Column(name = "challenger_id", nullable = false)
    String challengerId;

    @Column(name = "challenge_id", nullable = false)
    String challengeId;

    @Column(name = "solution_id")
    String solutionId;
}
