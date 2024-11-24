package vn.edu.likelion.front_ice.dto.response.challenge;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class ParticipationSubmissionCount {
    Long peopleParticipated;
    Long peopleSubmitted;
}
