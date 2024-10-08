package vn.edu.likelion.front_ice.controller;

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
import vn.edu.likelion.front_ice.service.gdrive.GoogleDriveService;

import java.io.File;
import java.io.IOException;

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
public class   ChallengerController {

    @Autowired
    ChallengerService challengerService;

    @Autowired
    private ResponseUtil responseUtil;
    
    @Autowired
    private GoogleDriveService googleDriveService;

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
            @RequestParam("accountId") String accountId,
            @RequestParam("image") MultipartFile file) throws
            IOException {
        if (file.isEmpty()) {
            throw new AppException(ErrorCode.PHOTO_UPLOAD_FAILED);
        }
        File tempFile = File.createTempFile("temp", null);
        file.transferTo(tempFile);

        return responseUtil.successResponse(googleDriveService.uploadChallengerAvatar(accountId,tempFile));

    }


    @GetMapping(ApiEndpoints.PROFILE_API + ApiEndpoints.GET_BY_ID)
    @PreAuthorize("hasAuthority('ROLE_CHALLENGER')")
    public ResponseEntity<RestAPIResponse<Object>> getDetailsProfile(@PathVariable(value = "id") String id) {
        return responseUtil.successResponse(challengerService.getDetailsProfile(id));
    }

}
