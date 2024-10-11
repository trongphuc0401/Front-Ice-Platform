package vn.edu.likelion.front_ice.dto.response.challenge;

import lombok.*;
import lombok.experimental.FieldDefaults;
import vn.edu.likelion.front_ice.common.enums.StatusChallenge;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChallengeResponse {

    String title;

    String categoryId;

    String technicalId;

    String levelId;

    LocalDateTime openDate;

    LocalDateTime closeDate;

    int isPremium;

    Integer score;

    String description;

    StatusChallenge status;

    Double price;

    String message;
}
