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

    @GetMapping(ApiEndpoints.PROFILE_API + ApiEndpoints.GET_BY_ID)
    @PreAuthorize("hasAuthority('ROLE_RECRUITER')")
    public ResponseEntity<RestAPIResponse<Object>> getDetailsProfile(@PathVariable(value = "id") String id) {
        return responseUtil.successResponse(recruiterService.getDetailsProfile(id));
    }

    @PostMapping(ApiEndpoints.UPLOAD_AVATAR)
    public ResponseEntity<RestAPIResponse<Object>> uploadAvatar(
            @RequestParam("accountId") String accountId,
            @RequestParam("image") MultipartFile file) throws IOException {


        if (file.isEmpty()) {
            throw new AppException(ErrorCode.PHOTO_UPLOAD_FAILED);
        }

        String originalFilename = file.getOriginalFilename();
        String contentType = file.getContentType();

        if (originalFilename == null || !HelperUtil.isImageFile(originalFilename, contentType)) {
            throw new AppException(ErrorCode.INVALID_IMAGE_FORMAT); // Ném lỗi định dạng ảnh không hợp lệ
        }

        File tempFile = File.createTempFile("recruiter_", accountId);
        file.transferTo(tempFile);


        return responseUtil.successResponse(googleDriveService.uploadRecruiterAvatar(accountId, tempFile));
    }




}
