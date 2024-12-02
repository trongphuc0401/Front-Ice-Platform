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
    AssetsResponse assets;
    FigmaResponse figma;
}
