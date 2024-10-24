package vn.edu.likelion.front_ice.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.SQLRestriction;
import vn.edu.likelion.front_ice.common.constants.SQLRestrictions;

@Entity
@Table(name = "tbl_preview")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@SQLRestriction(SQLRestrictions.SQL_DELETE_CONDITION)
public class PreviewEntity extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "challenge_id", nullable = true)
    ChallengeEntity challenge;

    @Column(columnDefinition = "TEXT")
    String url;

    @Column(columnDefinition = "TEXT")
    String label;
}
