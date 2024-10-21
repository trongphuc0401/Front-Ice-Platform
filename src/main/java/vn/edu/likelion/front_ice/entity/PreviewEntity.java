package vn.edu.likelion.front_ice.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "tbl_preview")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PreviewEntity extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "challenge_id", nullable = true)
    ChallengeEntity challenge;

    @Column(columnDefinition = "TEXT")
    String url;

    @Column(columnDefinition = "TEXT")
    String label;
}
