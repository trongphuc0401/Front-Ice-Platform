package vn.edu.likelion.front_ice.dto.request.solution;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateSolutionRequest {

    String urlProduct;

    String urlRepository;

    String title;

    String description;

    String note;

}
