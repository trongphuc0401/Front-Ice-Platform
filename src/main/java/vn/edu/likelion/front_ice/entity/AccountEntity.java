package vn.edu.likelion.front_ice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;


/**
 * AccountEntity -
 *
 * @param
 * @return
 * @throws
 */
@Entity
@Table(name = "tbl_account")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountEntity extends BaseEntity {



}
