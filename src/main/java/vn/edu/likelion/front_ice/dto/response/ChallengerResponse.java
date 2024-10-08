package vn.edu.likelion.front_ice.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import vn.edu.likelion.front_ice.common.enums.Role;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChallengerResponse {
    String firstName;
    String lastName;
    String email;
    Role role;
    String accountId;
    String levelId;
    String level;
    int isPremium ;
    String urlGithub;
    int score;
}
