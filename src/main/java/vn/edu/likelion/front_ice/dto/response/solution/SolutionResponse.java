package vn.edu.likelion.front_ice.dto.response.solution;

import jakarta.persistence.Column;
import lombok.*;
import lombok.experimental.FieldDefaults;
import vn.edu.likelion.front_ice.common.enums.StatusSolution;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SolutionResponse {
    String challengerId;

    String challengeId;

    String urlProduct;

    String urlRepository;

    String title;

    String description;

    String note;

    StatusSolution statusSolution;

}
