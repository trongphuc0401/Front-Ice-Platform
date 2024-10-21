package vn.edu.likelion.front_ice.dto.request.recruiter;

import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * CreationRecruiter -
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
public class CreateRecruiterRequest {
    String name; // đặt cho có lệ nhớ thay đổi nha mấy anh
}
