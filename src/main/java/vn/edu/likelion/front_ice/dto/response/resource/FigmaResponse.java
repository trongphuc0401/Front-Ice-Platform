package vn.edu.likelion.front_ice.dto.response.resource;

import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * FigmaResponse -
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
public class FigmaResponse {
    String figmaId;

    String  figmaName;

    long figmaSize;
}
