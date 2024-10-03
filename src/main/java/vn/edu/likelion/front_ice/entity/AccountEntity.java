package vn.edu.likelion.front_ice.entity;

import jakarta.persistence.*;
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

    @Column(unique = true, nullable = false, length = 50)
    String email;

    @Column(nullable = false, length = 50)
    String password;

    @Enumerated(EnumType.STRING) // Sử dụng EnumType.STRING để lưu giá trị enum dưới dạng chuỗi trong DB
    @Column(nullable = false)
    AccountType accountType;

    @Column(nullable = false)
    int status; // Kiểu dữ liệu bool tương ứng với boolean trong Java

    @Column(length = 50) // Banner có thể null
    String banner;

    @Column(length = 50) // Avatar có thể null
    String avatar;

    @Column(length = 50) // Phone có thể null
    String phone;

    @Column(nullable = false, length = 50)
    String firstName;

    @Column(nullable = false, length = 50)
    String lastName;

}
