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
import vn.edu.likelion.front_ice.common.exceptions.SuccessCode;
import vn.edu.likelion.front_ice.common.utils.HelperUtil;
import vn.edu.likelion.front_ice.dto.request.challenger.UpdateProfileChallengerRequest;
import vn.edu.likelion.front_ice.security.SecurityUtil;
import vn.edu.likelion.front_ice.service.firebase.FirebaseService;
import vn.edu.likelion.front_ice.service.gdrive.GoogleDriveService;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
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

    @Autowired
    private FirebaseService firebaseService;

//    @PostMapping(ApiEndpoints.FOLLOW)
//    @PreAuthorize("hasAuthority('ROLE_CHALLENGER')")
//    public ResponseEntity<RestAPIResponse<Object>> follow(@RequestBody FollowRequest followRequest) {
//        return responseUtil.successResponse(challengerService.follow(followRequest));
//    }
//
//    @GetMapping(ApiEndpoints.GET_FOLLOW)
//    @PreAuthorize("hasAuthority('ROLE_CHALLENGER')")
//    public ResponseEntity<RestAPIResponse<Object>> follow(@RequestParam Long id) {
//        return responseUtil.successResponse(challengerService.getFollow(id));
//    }

    @PostMapping(ApiEndpoints.UPLOAD_AVATAR)
    public ResponseEntity<RestAPIResponse<Object>> uploadAvatar(
            @RequestParam("image") MultipartFile file){
        if (file.isEmpty()) {
            throw new AppException(ErrorCode.PHOTO_UPLOAD_FAILED);
        }
        return responseUtil.successResponse(firebaseService.uploadChallengerAvatar(file));
    }

    @PostMapping(ApiEndpoints.UPLOAD_BANNER)
    public ResponseEntity<RestAPIResponse<Object>> uploadBanner(
            @RequestParam("image") MultipartFile file){
        if (file.isEmpty()) {
            throw new AppException(ErrorCode.PHOTO_UPLOAD_FAILED);
        }
        return responseUtil.successResponse(firebaseService.uploadChallengerBanner(file));
    }

    @GetMapping(ApiEndpoints.PROFILE_API)
    @PreAuthorize("hasAuthority('ROLE_CHALLENGER')")
    public ResponseEntity<RestAPIResponse<Object>> getDetailsProfile(@RequestHeader("Authorization") String authorizationHeader) {
        String token = securityUtil.extractJwtFromHeader(authorizationHeader);
        return responseUtil.successResponse(challengerService.getDetailsProfile(token));
    }

    @PutMapping(ApiEndpoints.PROFILE_API)
    public ResponseEntity<RestAPIResponse<Object>> updateProfile(@RequestBody UpdateProfileChallengerRequest updateProfileChallengerRequest)
            throws GeneralSecurityException, IOException {
        challengerService.updateProfile(updateProfileChallengerRequest);
        return responseUtil.successResponse(SuccessCode.UPDATE_CHALLENGER_SUCCESSFUL);
    }

    @GetMapping(ApiEndpoints.OVERVIEW)
    @PreAuthorize("hasAuthority('ROLE_CHALLENGER')")
    public ResponseEntity<RestAPIResponse<Object>> getOverview() {
        return responseUtil.successResponse(challengerService.getOverviewProfile());
    }


    @PostMapping(ApiEndpoints.UPLOAD_CV)
    @PreAuthorize("hasAuthority('ROLE_CHALLENGER')")
    public ResponseEntity<RestAPIResponse<Object>> uploadCV(
            @RequestParam("cv") MultipartFile file) {

        if (file.isEmpty()) {
            throw new AppException(ErrorCode.CV_UPLOAD_FAILED);
        }

        return responseUtil.successResponse(googleDriveService.uploadCV(file));


    }



}
