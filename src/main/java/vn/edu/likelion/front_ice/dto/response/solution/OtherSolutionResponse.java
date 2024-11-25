package vn.edu.likelion.front_ice.dto.response.solution;

import lombok.*;
import lombok.experimental.FieldDefaults;
import vn.edu.likelion.front_ice.common.enums.Level;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OtherSolutionResponse {
    Long id;
    String title;
    String description;
    String urlProduct;
    String urlRepository;
    String note;
    String challengerFirstName;
    String challengerLastName;
    Level challengerLevel;
    String challengerAvatar;
}
