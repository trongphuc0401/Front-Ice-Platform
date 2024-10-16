package vn.edu.likelion.front_ice.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;


/**
 * ResourceEntity -
 *
 * @param
 * @return
 * @throws
 */
@Entity
@Table(name = "tbl_resource")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResourceEntity extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "challenge_id", nullable = false)
    ChallengeEntity challenge;

    @Column(name = "assets_url", columnDefinition = "TEXT")
    String assetsUrl;

    @Column(name = "assets_name", length = 255)
    String assetsName;

    @Column(name = "assets_size")
    Long assetsSize;


    @Column(name = "figma_url", length = 255)
    String figmaUrl;

    @Column(name = "figma_name", length = 255)
    String figmaName;

    @Column(name = "figma_size")
    Integer figmaSize;

}

