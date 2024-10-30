package vn.edu.likelion.front_ice.dto.request.follow;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FollowRequest {
    Long challengerId;
    Long recruiterId;
}
