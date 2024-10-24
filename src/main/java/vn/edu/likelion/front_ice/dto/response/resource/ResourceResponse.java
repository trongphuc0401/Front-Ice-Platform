package vn.edu.likelion.front_ice.dto.response.resource;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResourceResponse {
    String assetsUrl;
    String assetsName;
    Long assetsSize;
    String figmaUrl;
    String figmaName;
    Long figmaSize;
}
