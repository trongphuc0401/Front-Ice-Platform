package vn.edu.likelion.front_ice.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

/**
 * UploadAvatarResponse -
 *
 * @param
 * @return
 * @throws
 */
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UploadAvatarResponse {
    String url;
}
