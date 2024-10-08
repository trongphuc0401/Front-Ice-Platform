package vn.edu.likelion.front_ice.dto.response.recruiter;

import lombok.*;
import lombok.experimental.FieldDefaults;
import vn.edu.likelion.front_ice.common.enums.Role;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RecruiterResponse {
    String firstName;
    String lastName;
    String email;
    Role role;
    String accountId;
    String name;
    String description;
    String urlWebsite;
    String address;
}
