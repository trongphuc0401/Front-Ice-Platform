package vn.edu.likelion.front_ice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.SQLRestriction;
import vn.edu.likelion.front_ice.common.constants.SQLRestrictions;


/**
 * FollowEntity -
 *
 * @param
 * @return
 * @throws
 */
@Entity
@Table(name = "tbl_follow")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@SQLRestriction(SQLRestrictions.SQL_DELETE_CONDITION)
public class FollowEntity extends BaseEntity {

    @Column
    String challengerId;

    @Column
    String recruiterId;
}
