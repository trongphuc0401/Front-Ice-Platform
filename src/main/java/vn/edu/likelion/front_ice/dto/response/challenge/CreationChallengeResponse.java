package vn.edu.likelion.front_ice.dto.response.challenge;

import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * CreationChallengeRequest -
 *
 * @param
 * @return
 * @throws
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreationChallengeResponse {
    String challenge; // đặt cho có lệ nhớ thay đổi nha mấy anh
}
