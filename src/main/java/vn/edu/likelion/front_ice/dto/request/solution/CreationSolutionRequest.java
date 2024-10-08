package vn.edu.likelion.front_ice.dto.request.solution;

import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * CreationSolutionRequest -
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

public class CreationSolutionRequest {
    String title;
}
