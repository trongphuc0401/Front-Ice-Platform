package vn.edu.likelion.front_ice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.likelion.front_ice.common.api.ResponseUtil;
import vn.edu.likelion.front_ice.common.api.RestAPIResponse;
import vn.edu.likelion.front_ice.common.constants.ApiEndpoints;
import vn.edu.likelion.front_ice.common.exceptions.AppException;
import vn.edu.likelion.front_ice.common.exceptions.ErrorCode;
import vn.edu.likelion.front_ice.service.gdrive.GoogleDriveService;

import java.io.File;
import java.io.IOException;

/**
 * MentorController -
 *
 * @param
 * @return
 * @throws
 */
@RestController
@RequestMapping(ApiEndpoints.MENTOR_API)
@RequiredArgsConstructor
public class MentorController {
    @Autowired
    private ResponseUtil responseUtil;
    @Autowired
    private GoogleDriveService googleDriveService;

    @PostMapping(ApiEndpoints.UPLOAD_AVATAR)
    public ResponseEntity<RestAPIResponse<Object>> uploadAvatar(
            @RequestParam("accountId") String accountId,
            @RequestParam("image") MultipartFile file) throws
            IOException {
        if (file.isEmpty()) {
            throw new AppException(ErrorCode.PHOTO_UPLOAD_FAILED);
        }
        File tempFile = File.createTempFile("mentor", accountId);
        file.transferTo(tempFile);

        return responseUtil.successResponse(googleDriveService.uploadMentorAvatar(accountId,tempFile));

    }
}
