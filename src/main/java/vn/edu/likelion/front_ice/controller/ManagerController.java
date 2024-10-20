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
import vn.edu.likelion.front_ice.entity.ChallengeEntity;
import vn.edu.likelion.front_ice.entity.ChallengerEntity;
import vn.edu.likelion.front_ice.repository.ChallengeRepository;
import vn.edu.likelion.front_ice.security.SecurityUtil;
import vn.edu.likelion.front_ice.service.gdrive.GoogleDriveService;
import vn.edu.likelion.front_ice.service.staff.StaffService;

import java.io.File;
import java.io.IOException;

/**
 * ManagerController -
 *
 * @param
 * @return
 * @throws
 */
@RestController
@RequestMapping(ApiEndpoints.MANAGER_API)
@RequiredArgsConstructor
public class ManagerController {

    @Autowired
    private ResponseUtil responseUtil;

    @Autowired
    private StaffService staffService;

    @Autowired
    private GoogleDriveService googleDriveService;
    @Autowired private SecurityUtil securityUtil;
    @Autowired private ChallengeRepository challengeRepository;

    @GetMapping(ApiEndpoints.PROFILE_API + ApiEndpoints.GET_BY_ID)
    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
    public ResponseEntity<RestAPIResponse<Object>> getDetailsProfile(@PathVariable(value = "id") String id) {
        return responseUtil.successResponse(staffService.getDetailsProfile(id));
    }

    @PostMapping(ApiEndpoints.UPLOAD_AVATAR)
    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
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

        File tempFile = File.createTempFile("manager_","");
        file.transferTo(tempFile);

        return responseUtil.successResponse(googleDriveService.uploadManagerAvatar(token,tempFile));

    }

    @PostMapping(ApiEndpoints.UPLOAD_ASSETS)
    public ResponseEntity<RestAPIResponse<Object>> uploadAssets(
            @RequestParam("challengeId") String challengeId,
            @RequestParam("assets") MultipartFile file) throws IOException {

        if (file.isEmpty()) {
            throw new AppException(ErrorCode.ASSETS_UPLOAD_FAILED);
        }

        ChallengeEntity challengeEntity = challengeRepository.findById(challengeId)
                .orElseThrow(() -> new AppException(ErrorCode.CHALLENGE_NOT_EXIST));

        File tempFile = File.createTempFile("resource_"+challengeEntity
                .getTitle()
                .toLowerCase()
                .replace(" ", "-")
                +"_", ".zip");

        try {
            file.transferTo(tempFile);

            return responseUtil.successResponse(googleDriveService.uploadAssets(challengeId, tempFile));

        } finally {
            if (tempFile.exists()) {
                tempFile.delete();
            }
        }
    }


    @PostMapping(ApiEndpoints.UPLOAD_FIGMA)
    public ResponseEntity<RestAPIResponse<Object>> uploadFigma(
            @RequestParam("challengeId") String challengeId,
            @RequestParam("figma") MultipartFile file) throws IOException {

        if (file.isEmpty()) {
            throw new AppException(ErrorCode.ASSETS_UPLOAD_FAILED);
        }

        ChallengeEntity challengeEntity = challengeRepository.findById(challengeId)
                .orElseThrow(() -> new AppException(ErrorCode.CHALLENGER_NOT_EXIST));

        File tempFile = File.createTempFile("figma_"+challengeEntity
                .getTitle()
                .toLowerCase()
                .replace(" ", "-")
                +"_", ".zip");

        try {
            file.transferTo(tempFile);
            return responseUtil.successResponse(googleDriveService.uploadFigma(challengeId, tempFile));
        } finally {
            if (tempFile.exists()) {
                tempFile.delete();
            }
        }
    }

    @PostMapping(ApiEndpoints.UPLOAD_DESKTOP_DESIGN)
    public ResponseEntity<RestAPIResponse<Object>> uploadDesktopDesign(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestParam("desktop") MultipartFile file) throws IOException {

        if (file.isEmpty()) {
            throw new AppException(ErrorCode.PHOTO_UPLOAD_FAILED);
        }
        securityUtil.extractJwtFromHeader(authorizationHeader);
        String originalFilename = file.getOriginalFilename();
        String contentType = file.getContentType();

        if (originalFilename == null || !HelperUtil.isImageFile(originalFilename, contentType)) {
            throw new AppException(ErrorCode.INVALID_IMAGE_FORMAT);
        }

        File tempFile = File.createTempFile("desktop"
                +"_", ".zip");

        try {
            file.transferTo(tempFile);
            return responseUtil.successResponse(googleDriveService.uploadImageDesktop(tempFile));
        } finally {
            if (tempFile.exists()) {
                tempFile.delete();
            }
        }
    }

    @PostMapping(ApiEndpoints.UPLOAD_MOBILE_DESIGN)
    public ResponseEntity<RestAPIResponse<Object>> uploadMobileDesign(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestParam("mobile") MultipartFile file) throws IOException {

        if (file.isEmpty()) {
            throw new AppException(ErrorCode.PHOTO_UPLOAD_FAILED);
        }
        securityUtil.extractJwtFromHeader(authorizationHeader);
        String originalFilename = file.getOriginalFilename();
        String contentType = file.getContentType();

        if (originalFilename == null || !HelperUtil.isImageFile(originalFilename, contentType)) {
            throw new AppException(ErrorCode.INVALID_IMAGE_FORMAT);
        }

        File tempFile = File.createTempFile("mobile"
                +"_", ".zip");

        try {
            file.transferTo(tempFile);
            return responseUtil.successResponse(googleDriveService.uploadImageMobile(tempFile));
        } finally {
            if (tempFile.exists()) {
                tempFile.delete();
            }
        }
    }

    @PostMapping(ApiEndpoints.UPLOAD_TABLET_DESIGN)
    public ResponseEntity<RestAPIResponse<Object>> uploaTabletDesign(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestParam("tablet") MultipartFile file) throws IOException {

        if (file.isEmpty()) {
            throw new AppException(ErrorCode.PHOTO_UPLOAD_FAILED);
        }
        securityUtil.extractJwtFromHeader(authorizationHeader);
        String originalFilename = file.getOriginalFilename();
        String contentType = file.getContentType();

        if (originalFilename == null || !HelperUtil.isImageFile(originalFilename, contentType)) {
            throw new AppException(ErrorCode.INVALID_IMAGE_FORMAT);
        }

        File tempFile = File.createTempFile("tablet"
                +"_", ".zip");

        try {
            file.transferTo(tempFile);
            return responseUtil.successResponse(googleDriveService.uploadImageTablet(tempFile));
        } finally {
            if (tempFile.exists()) {
                tempFile.delete();
            }
        }
    }




}
