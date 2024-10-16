package vn.edu.likelion.front_ice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.edu.likelion.front_ice.common.api.ResponseUtil;
import vn.edu.likelion.front_ice.common.api.RestAPIResponse;
import vn.edu.likelion.front_ice.common.constants.ApiEndpoints;
import vn.edu.likelion.front_ice.common.exceptions.AppException;
import vn.edu.likelion.front_ice.common.exceptions.ErrorCode;
import vn.edu.likelion.front_ice.entity.ResourceEntity;
import vn.edu.likelion.front_ice.repository.ResourceRepository;
import vn.edu.likelion.front_ice.security.SecurityUtil;
import vn.edu.likelion.front_ice.service.challenge.ChallengeService;
import vn.edu.likelion.front_ice.service.gdrive.GoogleDriveServiceImpl;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;

/**
 * ChallengeController -
 *
 * @param
 * @return
 * @throws
 */
@RestController
@RequestMapping(ApiEndpoints.CHALLENGE_API)
@RequiredArgsConstructor
public class ChallengeController {

    @Autowired
    private ResponseUtil responseUtil;
    @Autowired
    private ChallengeService challengeService;
    @Autowired private ResourceRepository resourceRepository;
    @Autowired private GoogleDriveServiceImpl googleDriveServiceImpl;
    @Autowired private SecurityUtil securityUtil;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_RECRUITER', 'ROLE_MANAGER', 'ROLE_ADMIN')")
    public ResponseEntity<RestAPIResponse<Object>> getChallengeByCategory(@RequestParam String category
            , @RequestParam int sizeNo, @RequestParam int pageNo) {
        return responseUtil.successResponse(challengeService
                .getPaginationChallengeByCategory(category, sizeNo, pageNo));
    }

    @GetMapping(ApiEndpoints.DOWNLOAD_ASSETS)
    public ResponseEntity<Resource> downloadAssets(
            @RequestHeader("Authorization") String authorizationHeader,
            @PathVariable(value = "id") String challengeId)
            throws IOException, GeneralSecurityException {

        String token = securityUtil.extractJwtFromHeader(authorizationHeader);
        if (token.isEmpty()) {
            throw new AppException(ErrorCode.INVALID_JWT_TOKEN);
        }

        InputStream fileStream = googleDriveServiceImpl.downloadAssets(challengeId);

        InputStreamResource resource = new InputStreamResource(fileStream);

        ResourceEntity resourceEntity = resourceRepository.findByChallengeId(challengeId)
                .orElseThrow(() -> new AppException(ErrorCode.CHALLENGE_NOT_EXIST));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resourceEntity.getAssetsName() + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }


}
