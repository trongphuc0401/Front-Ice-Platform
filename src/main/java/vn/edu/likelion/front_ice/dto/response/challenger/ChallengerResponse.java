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
    String accountId;
    AccountResponse accountResponse;
    String levelId;
    String level;
    int isPremium ;
    String urlGithub;
    int score;
    String urlCV;
    String urlPortfolio;
    String urlCodepen;
    String urlGitLab;
    String urlStackOverflow;
    String urlLinkedIn;

    int totalJoinedChallenge; // tổng số challenge đã tham gia
    int totalSubmittedChallenge; // tổng số challenge đã submit solution

    NextLevelResponse nextLevel; // thể hiện cần bao nhiêu điểm nữa để lên level kế tiếp
}

