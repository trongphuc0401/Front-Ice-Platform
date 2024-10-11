package vn.edu.likelion.front_ice.dto.request.challenge;

import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * UpdateChallengeRequest -
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
public class UpdateChallengeRequest {
    String title;
}
