package vn.edu.likelion.front_ice.dto.response.resource;

import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * AssetsResponse -
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
public class AssetsResponse {

    String assetsId;

    String  assetsName;

    long assetsSize;
}
