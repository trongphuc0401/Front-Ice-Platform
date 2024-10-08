package vn.edu.likelion.front_ice.dto.response.challenger;

import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * ChallengerResponse -
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
public class ChallengerResponse {
    String title;
}
