package vn.edu.likelion.front_ice.dto.response.challenger;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NextLevelResponse {
    int score;
    String rank;
}
