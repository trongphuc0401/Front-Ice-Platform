package vn.edu.likelion.front_ice.dto.response.challenge;

import lombok.*;
import lombok.experimental.FieldDefaults;
import vn.edu.likelion.front_ice.common.enums.TypeChallenge;
import vn.edu.likelion.front_ice.dto.response.category.CategoryResponse;
import vn.edu.likelion.front_ice.dto.response.challengepoint.ChallengePointResponse;
import vn.edu.likelion.front_ice.dto.response.preview.PreviewResponse;
import vn.edu.likelion.front_ice.dto.response.resource.ResourceResponse;
import vn.edu.likelion.front_ice.dto.response.solution.OtherSolutionResponse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DetailChallengeResponse {
    Long id;
    String challengeCode;
    String title;
    String description;
    LocalDateTime openDate;
    LocalDateTime closeDate;
    TypeChallenge typeChallenge;
    String message;
    String banner;
    String assets;
    Boolean hidden;
    String accessStatus;
    String accessMessage;
    Long peopleParticipated;
    Long peopleSubmitted;

    CategoryResponse category;
    Set<String> technicals;
    ChallengePointResponse challengePoint;
    Set<PreviewResponse> previews;
    List<OtherSolutionResponse> otherSolutions;
}
