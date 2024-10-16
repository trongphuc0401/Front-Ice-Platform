package vn.edu.likelion.front_ice.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import vn.edu.likelion.front_ice.common.enums.Difficulty;
import vn.edu.likelion.front_ice.common.enums.Level;

@Entity
@Table(name = "tbl_challenge_point")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChallengePointEntity extends BaseEntity{

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    Level level;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    Difficulty difficulty;

    @Column(nullable = false)
    Integer points;
}
