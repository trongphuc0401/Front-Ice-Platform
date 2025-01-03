package vn.edu.likelion.front_ice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.likelion.front_ice.common.api.ResponseUtil;
import vn.edu.likelion.front_ice.common.api.RestAPIResponse;
import vn.edu.likelion.front_ice.common.api.RestAPIStatus;
import vn.edu.likelion.front_ice.common.constants.ApiEndpoints;
import vn.edu.likelion.front_ice.common.exceptions.ErrorCode;
import vn.edu.likelion.front_ice.common.exceptions.SuccessCode;
import vn.edu.likelion.front_ice.dto.request.comment.CreateCommentRequest;
import vn.edu.likelion.front_ice.dto.response.comment.CommentResponse;
import vn.edu.likelion.front_ice.entity.CommentEntity;
import vn.edu.likelion.front_ice.mapper.CommentMapper;
import vn.edu.likelion.front_ice.service.comment.CommentService;

import java.util.Optional;

/**
 * CommentController -
 *
 * @param
 * @return
 * @throws
 */
@RestController
@RequestMapping(ApiEndpoints.COMMENT_API)
@RequiredArgsConstructor
public class CommentController {

    private final ResponseUtil responseUtil;
    private final CommentService commentService;
    private final CommentMapper commentMapper;

    @PostMapping
    public ResponseEntity<RestAPIResponse<Object>> createComment(@RequestBody CreateCommentRequest createCommentRequest) {

        Optional<CommentEntity> commentEntity = commentService.create(createCommentRequest);

        if (commentEntity.isPresent()) {
           CommentResponse commentResponse = commentMapper.toCommentResponse(commentEntity.get());
            return responseUtil.successResponse(SuccessCode.CREATE_COMMENT_SUCCESSFUL,commentResponse);
        }

        return  responseUtil.buildResponse(RestAPIStatus.BAD_REQUEST,ErrorCode.CREATE_FAILED, HttpStatus.CREATED);
    }
}
