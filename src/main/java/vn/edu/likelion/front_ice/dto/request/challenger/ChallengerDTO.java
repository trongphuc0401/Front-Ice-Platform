package vn.edu.likelion.front_ice.dto.request.challenger;

import lombok.Builder;
import lombok.Data;
import vn.edu.likelion.front_ice.common.enums.Level;

@Data
@Builder(setterPrefix = "with")
public class ChallengerDTO {
    Level levelChallenger;
    int score;
}
