package vn.edu.likelion.front_ice.dto.response.challenger;

import lombok.*;
import lombok.experimental.FieldDefaults;
import vn.edu.likelion.front_ice.common.enums.Role;
import vn.edu.likelion.front_ice.dto.response.account.AccountResponse;

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
    String banner;
    String avatar;
    String phone;
    Long levelId;
    String level;
    Boolean isPremium ;
    String urlGithub;
    Integer score;
    String urlCV;
    String urlPortfolio;
    String urlCodepen;
    String urlGitLab;
    String urlStackOverflow;
    String urlLinkedIn;
}

