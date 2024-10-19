package vn.edu.likelion.front_ice.dto.response.technical;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TechnicalResponse {
    String id;
    String title;
}
