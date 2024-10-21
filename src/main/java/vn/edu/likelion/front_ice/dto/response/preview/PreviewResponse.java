package vn.edu.likelion.front_ice.dto.response.preview;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PreviewResponse {
    String id;
    String imageUrl;
    String label;
}