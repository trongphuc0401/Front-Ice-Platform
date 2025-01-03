package vn.edu.likelion.front_ice.dto.request.comment;

import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * CreateCommentRequest -
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
public class CreateCommentRequest {

    String content;

    String solutionCode;

    Long parentId;
}
