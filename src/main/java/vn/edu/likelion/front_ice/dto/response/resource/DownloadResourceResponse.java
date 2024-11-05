package vn.edu.likelion.front_ice.dto.response.resource;

import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * DownloadResourceResponse -
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
public class DownloadResourceResponse {

    String assetsId;

    String assetsName;

    Long assetsSize;

    String figmaId;

    String figmaName;

    Long figmaSize;


}
