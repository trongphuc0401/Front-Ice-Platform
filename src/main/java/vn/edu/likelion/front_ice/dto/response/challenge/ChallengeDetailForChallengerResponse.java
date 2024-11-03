package vn.edu.likelion.front_ice.dto.response.challenge;

import lombok.*;
import lombok.experimental.FieldDefaults;
import vn.edu.likelion.front_ice.common.enums.TypeChallenge;
import vn.edu.likelion.front_ice.dto.response.category.CategoryResponse;
import vn.edu.likelion.front_ice.dto.response.challengepoint.ChallengePointResponse;
import vn.edu.likelion.front_ice.dto.response.preview.PreviewResponse;
import vn.edu.likelion.front_ice.dto.response.resource.ResourceResponse;
import vn.edu.likelion.front_ice.dto.response.technical.TechnicalResponse;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChallengeDetailForChallengerResponse {
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

    CategoryResponse category;
    Set<String> technicals;
    ChallengePointResponse challengePoint;
    Set<PreviewResponse> previews;
    ResourceResponse resource;
//    List<SolutionResponse> otherSolutions;
}
