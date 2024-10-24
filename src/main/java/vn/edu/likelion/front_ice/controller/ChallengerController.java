package vn.edu.likelion.front_ice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.likelion.front_ice.common.api.ResponseUtil;
import vn.edu.likelion.front_ice.common.api.RestAPIResponse;
import vn.edu.likelion.front_ice.common.constants.ApiEndpoints;
import vn.edu.likelion.front_ice.common.exceptions.AppException;
import vn.edu.likelion.front_ice.common.exceptions.ErrorCode;
import vn.edu.likelion.front_ice.common.utils.HelperUtil;
import vn.edu.likelion.front_ice.security.SecurityUtil;
import vn.edu.likelion.front_ice.service.gdrive.GoogleDriveService;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.edu.likelion.front_ice.dto.request.follow.FollowRequest;
import vn.edu.likelion.front_ice.service.challenger.ChallengerService;

/**
 * ChallengerController -
 *
 * @param
 * @return
 * @throws
 */
@RestController
@RequestMapping(ApiEndpoints.CHALLENGER_API)
@RequiredArgsConstructor
public class ChallengerController {

    @Autowired
    ChallengerService challengerService;

    @Autowired
    private ResponseUtil responseUtil;
    
    @Autowired
    private GoogleDriveService googleDriveService;
    @Autowired private SecurityUtil securityUtil;

    @PostMapping(ApiEndpoints.FOLLOW)
    @PreAuthorize("hasAuthority('ROLE_CHALLENGER')")
    public ResponseEntity<RestAPIResponse<Object>> follow(@RequestBody FollowRequest followRequest) {
        return responseUtil.successResponse(challengerService.follow(followRequest));
    }

    @GetMapping(ApiEndpoints.GET_FOLLOW)
    @PreAuthorize("hasAuthority('ROLE_CHALLENGER')")
    public ResponseEntity<RestAPIResponse<Object>> follow(@RequestParam String id) {
        return responseUtil.successResponse(challengerService.getFollow(id));
    }
    
    @PostMapping(ApiEndpoints.UPLOAD_AVATAR)
    public ResponseEntity<RestAPIResponse<Object>> uploadAvatar(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestParam("image") MultipartFile file) throws
            IOException {
        if (file.isEmpty()) {
            throw new AppException(ErrorCode.PHOTO_UPLOAD_FAILED);
        }
        String token = securityUtil.extractJwtFromHeader(authorizationHeader);

        String originalFilename = file.getOriginalFilename();
        String contentType = file.getContentType();

        if (originalFilename == null || !HelperUtil.isImageFile(originalFilename, contentType)) {
            throw new AppException(ErrorCode.INVALID_IMAGE_FORMAT); // Ném lỗi định dạng ảnh không hợp lệ
        }
        File tempFile = File.createTempFile("challenger_", null);
        file.transferTo(tempFile);

        return responseUtil.successResponse(googleDriveService.uploadChallengerAvatar(token,tempFile));

    }

    @GetMapping(ApiEndpoints.PROFILE_API)
    @PreAuthorize("hasAuthority('ROLE_CHALLENGER')")
    public ResponseEntity<RestAPIResponse<Object>> getDetailsProfile(@RequestHeader("Authorization") String authorizationHeader) {
        String token = securityUtil.extractJwtFromHeader(authorizationHeader);
        return responseUtil.successResponse(challengerService.getDetailsProfile(token));
    }


    @PostMapping(ApiEndpoints.UPLOAD_CV)
    @PreAuthorize("hasAuthority('ROLE_CHALLENGER')")
    public ResponseEntity<RestAPIResponse<Object>> uploadCV(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestParam("cv") MultipartFile file) throws IOException {

        if (file.isEmpty()) {
            throw new AppException(ErrorCode.CV_UPLOAD_FAILED);
        }

        String token = securityUtil.extractJwtFromHeader(authorizationHeader);

        File tempFile = File.createTempFile("CV_", ".pdf");

        try {

            file.transferTo(tempFile);


            return responseUtil.successResponse(googleDriveService.uploadCV(token, tempFile));

        } finally {
            // Đảm bảo xóa file tạm sau khi sử dụng
            if (tempFile.exists()) {
                tempFile.delete();
            }
        }
    }



}
