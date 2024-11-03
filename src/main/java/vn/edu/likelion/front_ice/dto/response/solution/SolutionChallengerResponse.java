package vn.edu.likelion.front_ice.dto.response.solution;

import lombok.*;
import lombok.experimental.FieldDefaults;
import vn.edu.likelion.front_ice.dto.response.challengepoint.ChallengePointResponse;
import vn.edu.likelion.front_ice.dto.response.technical.TechnicalResponse;

import java.sql.Timestamp;
import java.util.Set;

/**
 * SolutionChallengerResponse -
 *
 * @param
 * @return
 * @throws
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SolutionChallengerResponse {

    String banner;

    Long submittedAt;

    String title;

    Set<TechnicalResponse> technicals;

    ChallengePointResponse challengePoint;

    Integer totalLike;

    Integer totalDislike;

    Integer totalComment;


}
