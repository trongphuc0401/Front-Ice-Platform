package vn.edu.likelion.front_ice.dto.response.challenge;

import lombok.*;
import lombok.experimental.FieldDefaults;
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
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DetailChallengeResponse {
    String id;
    String title;
    String description;
    LocalDateTime openDate;
    LocalDateTime closeDate;
    String statusChallenge;
    String typeChallenge;
    Double price;
    String message;
    String banner;
    String assets;
    Boolean isHidden;

    CategoryResponse category;
    Set<TechnicalResponse> technicals;
    ChallengePointResponse challengePoint;
    Set<PreviewResponse> previews;
}
