package vn.edu.likelion.front_ice.controller;

import com.nimbusds.oauth2.sdk.GeneralException;
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
import vn.edu.likelion.front_ice.common.api.RestAPIStatus;
import vn.edu.likelion.front_ice.common.constants.ApiEndpoints;
import vn.edu.likelion.front_ice.dto.response.UploadAvatarResponse;
import vn.edu.likelion.front_ice.service.gdrive.GoogleDriveService;

import java.io.File;
import java.io.IOException;

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
    private GoogleDriveService googleDriveService;

    @Autowired
    ResponseUtil responseUtil;

    @PostMapping("/upload")
    public ResponseEntity<RestAPIResponse<Object>> uploadAvatar(@RequestParam("image") MultipartFile file) throws
            IOException, GeneralException {

        File tempFile = File.createTempFile("temp", null);
        file.transferTo(tempFile);

        return responseUtil.successResponse(googleDriveService.uploadChallengerAvatar(tempFile));
    }
}
