package vn.edu.likelion.front_ice.dto.response.comment;

import lombok.*;
import lombok.experimental.FieldDefaults;
import vn.edu.likelion.front_ice.dto.response.account.AccountResponse;

/**
 * CommentResponse -
 *
 * @param
 * @return
 * @throws
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentResponse {

    Long id;

    String content;

    String commentId;

    Long parentId;

    Integer left;

    Integer right;

    Boolean isEdit;

    int replies;


}
