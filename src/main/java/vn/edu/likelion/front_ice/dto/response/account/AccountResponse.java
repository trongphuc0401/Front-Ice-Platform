package vn.edu.likelion.front_ice.dto.response.account;

import jakarta.persistence.Column;
import lombok.*;
import lombok.experimental.FieldDefaults;
import vn.edu.likelion.front_ice.common.enums.Role;

/**
 * AccountResponse -
 *
 * @param
 * @return
 * @throws
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountResponse {

    String email;

    Role role;

    int status;

    String banner;

    String avatar;

    String phone;

    String firstName;

    String lastName;

    int isAuthenticated ;
}
