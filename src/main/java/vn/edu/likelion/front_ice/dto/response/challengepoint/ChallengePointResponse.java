package vn.edu.likelion.front_ice.dto.response.challengepoint;

import lombok.*;
import lombok.experimental.FieldDefaults;
import vn.edu.likelion.front_ice.common.enums.Difficulty;
import vn.edu.likelion.front_ice.common.enums.Level;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChallengePointResponse {
    Level level;
    Difficulty difficulty;
    Integer points;
}
