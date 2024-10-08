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
import vn.edu.likelion.front_ice.service.gdrive.GoogleDriveService;
import vn.edu.likelion.front_ice.service.staff.StaffService;

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
    private StaffService staffService;
    @Autowired
    private GoogleDriveService googleDriveService;

    @GetMapping(ApiEndpoints.PROFILE_API + ApiEndpoints.GET_BY_ID)
    @PreAuthorize("hasAuthority('ROLE_MENTOR')")
    public ResponseEntity<RestAPIResponse<Object>> getDetailsProfile(@PathVariable(value = "id") String id) {
        return responseUtil.successResponse(staffService.getDetailsProfile(id));
    }

    @PostMapping(ApiEndpoints.UPLOAD_AVATAR)
    @PreAuthorize("hasAuthority('ROLE_MENTOR')")
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
