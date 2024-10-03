package vn.edu.likelion.front_ice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;
import vn.edu.likelion.front_ice.common.enums.AccountType;


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

    @Column(unique = true, nullable = false)
    String email;

    @Column()
    String password;

    @Column()
    AccountType accountType;

    @Column
    int status;









}
