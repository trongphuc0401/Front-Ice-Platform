package vn.edu.likelion.front_ice.dto.response.account;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RefreshTokenResponse {
    String accessToken;
    String refreshToken;
}
