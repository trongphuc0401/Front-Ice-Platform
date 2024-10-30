package vn.edu.likelion.front_ice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.likelion.front_ice.common.api.ResponseUtil;
import vn.edu.likelion.front_ice.common.api.RestAPIResponse;
import vn.edu.likelion.front_ice.common.constants.ApiEndpoints;
import vn.edu.likelion.front_ice.common.exceptions.AppException;
import vn.edu.likelion.front_ice.common.exceptions.ErrorCode;
import vn.edu.likelion.front_ice.common.utils.HelperUtil;
import vn.edu.likelion.front_ice.security.SecurityUtil;
import vn.edu.likelion.front_ice.service.firebase.FirebaseService;
import vn.edu.likelion.front_ice.service.firebase.FirebaseServiceImpl;
import vn.edu.likelion.front_ice.service.gdrive.GoogleDriveService;
import vn.edu.likelion.front_ice.service.recruiter.RecruiterService;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * RecruiterController -
 *
 * @param
 * @return
 * @throws
 */
@RestController
@RequestMapping(ApiEndpoints.RECRUITER_API)
@RequiredArgsConstructor
public class RecruiterController {

    @Autowired
    private ResponseUtil responseUtil;

    @Autowired
    private RecruiterService recruiterService;

    @Autowired
    private final GoogleDriveService googleDriveService;
    @Autowired private SecurityUtil securityUtil;
    @Autowired private FirebaseService firebaseService;

    @GetMapping(ApiEndpoints.PROFILE_API)
    @PreAuthorize("hasAuthority('ROLE_RECRUITER')")
    public ResponseEntity<RestAPIResponse<Object>> getDetailsProfile(
            @RequestHeader("Authorization") String authorizationHeader) {
        String token = securityUtil.extractJwtFromHeader(authorizationHeader);
        return responseUtil.successResponse(recruiterService.getDetailsProfile(token));
    }

    @PostMapping(ApiEndpoints.UPLOAD_AVATAR)
    public ResponseEntity<RestAPIResponse<Object>> uploadAvatar(
            @RequestParam("image") MultipartFile file) {

        if (file.isEmpty()) {
            throw new AppException(ErrorCode.PHOTO_UPLOAD_FAILED);
        }

        return responseUtil.successResponse(firebaseService.uploadRecruiterAvatar(file));
    }




}
