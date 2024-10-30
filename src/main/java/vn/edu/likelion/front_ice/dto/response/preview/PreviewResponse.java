package vn.edu.likelion.front_ice.dto.response.preview;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PreviewResponse {
    Long id;
    String url;
    String label;
}