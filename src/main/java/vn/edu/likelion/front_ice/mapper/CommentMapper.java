package vn.edu.likelion.front_ice.mapper;

import org.mapstruct.Mapper;
import org.springframework.data.util.Optionals;
import vn.edu.likelion.front_ice.dto.response.comment.CommentResponse;
import vn.edu.likelion.front_ice.entity.AccountEntity;
import vn.edu.likelion.front_ice.entity.CommentEntity;
import vn.edu.likelion.front_ice.entity.SolutionEntity;

import java.util.Optional;

/**
 * CommentMapper -
 *
 * @param
 * @return
 * @throws
 */
@Mapper(componentModel = "spring")
public interface CommentMapper {

    CommentResponse toCommentResponse(CommentEntity commentEntity);

}
