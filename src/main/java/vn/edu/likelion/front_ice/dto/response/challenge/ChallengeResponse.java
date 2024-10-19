package vn.edu.likelion.front_ice.dto.response.challenge;

import lombok.*;
import lombok.experimental.FieldDefaults;
import vn.edu.likelion.front_ice.common.enums.StatusChallenge;
import vn.edu.likelion.front_ice.common.enums.TypeChallenge;
import vn.edu.likelion.front_ice.dto.response.category.CategoryResponse;
import vn.edu.likelion.front_ice.dto.response.challengepoint.ChallengePointResponse;
import vn.edu.likelion.front_ice.dto.response.preview.PreviewResponse;
import vn.edu.likelion.front_ice.dto.response.technical.TechnicalResponse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChallengeResponse {

    String id;
    String title;
    CategoryResponse category;
    Set<TechnicalResponse> technicals;
    Set<PreviewResponse> previews;
    ChallengePointResponse challengePoint;
    LocalDateTime openDate;
    LocalDateTime closeDate;
    TypeChallenge typeChallenge;
    String description;
    StatusChallenge statusChallenge;
    Integer price;
    String brief;
    String assets;
    String message;
    Boolean isHidden;
    List<PreviewResponse> preview;
    Long createAt;
    Long updateAt;
}
