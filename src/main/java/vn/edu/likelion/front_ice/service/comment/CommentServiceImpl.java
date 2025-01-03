package vn.edu.likelion.front_ice.service.comment;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.likelion.front_ice.common.exceptions.AppException;
import vn.edu.likelion.front_ice.common.exceptions.ErrorCode;
import vn.edu.likelion.front_ice.dto.request.comment.CreateCommentRequest;
import vn.edu.likelion.front_ice.dto.response.comment.CommentResponse;
import vn.edu.likelion.front_ice.entity.ChallengeEntity;
import vn.edu.likelion.front_ice.entity.ChallengerEntity;
import vn.edu.likelion.front_ice.entity.CommentEntity;
import vn.edu.likelion.front_ice.entity.SolutionEntity;
import vn.edu.likelion.front_ice.repository.ChallengerRepository;
import vn.edu.likelion.front_ice.repository.CommentRepository;
import vn.edu.likelion.front_ice.repository.SolutionRepository;
import vn.edu.likelion.front_ice.security.SecurityUtil;

import java.util.List;
import java.util.Optional;

/**
 * CommentServiceImpl -
 *
 * @param
 * @return
 * @throws
 */
@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CommentServiceImpl implements CommentService {


    private final SolutionRepository solutionRepository;
    private final CommentRepository commentRepository;
    private final ChallengerRepository challengerRepository;

    @Override
    @Transactional
    public Optional<CommentEntity> create(CreateCommentRequest createCommentRequest) {
        // Bảo mật: Lấy email của người dùng hiện tại
        String email = SecurityUtil.getCurrentUserLogin()
                .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_EXIST));

        // Tìm challenger (người dùng) thông qua email
        ChallengerEntity challenger = challengerRepository.findByAccountEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.CHALLENGER_NOT_EXIST));

        // Kiểm tra solution tồn tại
        SolutionEntity solution = solutionRepository.findBySolutionCode(createCommentRequest.getSolutionCode())
                .orElseThrow(() -> new AppException(ErrorCode.SOLUTION_NOT_EXIST));

        // Tạo mới CommentEntity
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setContent(createCommentRequest.getContent());
        commentEntity.setSolution(solution);
        commentEntity.setChallenger(challenger);
        commentEntity.setIsEdit(false);

        Long parentId;

        // Kiểm tra xem đây là bình luận đầu tiên hay reply
        if (createCommentRequest.getParentId() == null) {
            // Bình luận đầu tiên
            parentId = 1L;
            Integer maxRight = commentRepository.findMaxRightBySolutionId(solution.getId());
            commentEntity.setLeft((maxRight == null ? 0 : maxRight) + 1);
            commentEntity.setRight(commentEntity.getLeft() + 1);
            commentEntity.setParentId(0L); // Bình luận gốc không có parent
            commentEntity.setReplies(0);
            commentEntity.setCommentId("0." + commentEntity.getLeft() + "." + commentEntity.getRight());
        } else {
            // Reply vào một bình luận khác
            parentId = createCommentRequest.getParentId();
            CommentEntity parentComment = commentRepository.findByParentId(parentId)
                    .orElseThrow(() -> new AppException(ErrorCode.PARENT_COMMENT_NOT_FOUND));

            // Kiểm tra parentComment có thuộc cùng solution không
            if (!parentComment.getSolution().getId().equals(solution.getId())) {
                throw new AppException(ErrorCode.PARENT_COMMENT_NOT_BELONG_SOLUTION);
            }

            // Cập nhật Nested Set để chèn bình luận mới
            updateNestedSetIndices(parentComment.getRight());

            // Gán left và right cho comment mới
            commentEntity.setLeft(parentComment.getRight());
            commentEntity.setRight(parentComment.getRight() + 1);
            commentEntity.setParentId(parentComment.getParentId()+1);

            // Tạo commentId dựa trên parentComment
            commentEntity.setCommentId(parentComment.getParentId() + "." + commentEntity.getLeft() + "." + commentEntity.getRight());

            // Cập nhật replies cho parent comment
            parentComment.setReplies(commentEntity.getReplies() + 1);
            commentRepository.save(parentComment);
        }

        // Lưu comment mới
        return Optional.of(commentRepository.save(commentEntity));
    }


    @Transactional protected void updateNestedSetIndices(Integer startRight) {
        commentRepository.incrementRightValues(startRight);
        commentRepository.incrementLeftValues(startRight);
    }

    @Override public Optional<CommentEntity> updateInfo(Long id, CommentResponse i) {
        return Optional.empty();
    }

    @Override public List<CommentEntity> saveAll(List<CommentEntity> ts) {
        return List.of();
    }

    @Override public void delete(Long id) {

    }

    @Override public void deleteAll(List<Long> listId) {

    }

    @Override public CommentEntity findById(Long id) {
        return null;
    }

    @Override public List<CommentEntity> findAll() {
        return List.of();
    }
}
