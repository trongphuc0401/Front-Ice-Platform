package vn.edu.likelion.front_ice.dto.request.challenger;

import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * CreationChallengerRequest -
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
public class CreateChallengerRequest {
    String title;
}
