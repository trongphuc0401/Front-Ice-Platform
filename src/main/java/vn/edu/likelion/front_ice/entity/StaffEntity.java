package vn.edu.likelion.front_ice.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.SQLRestriction;
import vn.edu.likelion.front_ice.common.constants.SQLRestrictions;
import vn.edu.likelion.front_ice.common.enums.Role;

import java.util.HashSet;
import java.util.Set;


/**
 * StaffEntity -
 *
 * @param
 * @return
 * @throws
 */
@Entity
@Table(name = "tbl_staff")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@SQLRestriction(SQLRestrictions.SQL_DELETE_CONDITION)
public class StaffEntity extends BaseEntity {
    @OneToOne
    @JoinColumn(name = "account_id", nullable = false)
    AccountEntity account;

    @Column
    String nameStaff;

    @Column
    Role role;
}
