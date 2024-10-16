package vn.edu.likelion.front_ice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;


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

}
