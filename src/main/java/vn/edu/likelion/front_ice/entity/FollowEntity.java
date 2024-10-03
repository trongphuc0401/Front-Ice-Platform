package vn.edu.likelion.front_ice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;


/**
 * FollowEntity -
 *
 * @param
 * @return
 * @throws
 */
@Entity
@Table(name = "tbl_follow")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FollowEntity extends BaseEntity {

    @Column
    String challenger_id;

    @Column
    String recruiter_id;
}
