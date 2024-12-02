package vn.edu.likelion.front_ice.dto.response.challenger;

import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * OverviewResponse -
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
public class OverviewResponse {

    String email;

    String firstName;

    String lastName;

    String avatar;

    Boolean isPremium;

    Long levelId;

    String level;

    Double score;

    Integer totalJoinedChallenge;

    Integer totalSubmittedChallenge;

    NextLevelResponse nextLevel;
}
