package vn.edu.likelion.front_ice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.likelion.front_ice.common.api.ResponseUtil;
import vn.edu.likelion.front_ice.common.api.RestAPIResponse;
import vn.edu.likelion.front_ice.common.constants.ApiEndpoints;
import vn.edu.likelion.front_ice.common.exceptions.SuccessCode;
import vn.edu.likelion.front_ice.common.query.SearchRequest;
import vn.edu.likelion.front_ice.dto.response.challenge.ResultPaginationResponse;
import vn.edu.likelion.front_ice.common.exceptions.AppException;
import vn.edu.likelion.front_ice.common.exceptions.ErrorCode;
import vn.edu.likelion.front_ice.dto.response.resource.DownloadResourceResponse;
import vn.edu.likelion.front_ice.entity.ChallengeEntity;
import vn.edu.likelion.front_ice.entity.ResourceEntity;
import vn.edu.likelion.front_ice.mapper.ChallengeMapper;
import vn.edu.likelion.front_ice.projection.resource.AssetsNameProjection;
import vn.edu.likelion.front_ice.projection.resource.FigmaNameProjection;
import vn.edu.likelion.front_ice.repository.ResourceRepository;
import vn.edu.likelion.front_ice.security.SecurityUtil;
import vn.edu.likelion.front_ice.service.challenge.ChallengeService;
import vn.edu.likelion.front_ice.service.gdrive.GoogleDriveServiceImpl;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

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
    @Autowired
    private ChallengeMapper challengeMapper;

    @GetMapping
    public ResponseEntity<RestAPIResponse<Object>> getAllChallenges(
            @RequestParam(defaultValue = "1") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize) {

        ResultPaginationResponse response = challengeService.getPaginationChallenge(pageNo, pageSize);
        return responseUtil.successResponse(SuccessCode.CHALLENGE_LIST_SUCCESS, response);
    }

    @GetMapping(ApiEndpoints.JOINED)
    public ResponseEntity<RestAPIResponse<Object>> getAllJoinedChallenges(
            @RequestParam(defaultValue = "1") int pageNo,
            @RequestParam(defaultValue = "6") int pageSize
    ) {
        ResultPaginationResponse response = challengeService.getPaginationJoinedChallenge(pageNo, pageSize);
        return responseUtil.successResponse(SuccessCode.CHALLENGE_LIST_SUCCESS, response);
    }

    @GetMapping(ApiEndpoints.GET_BY_ID)
    public ResponseEntity<RestAPIResponse<Object>> getChallengeDetail(@PathVariable("id") Long challengeId) {
        Object response  = challengeService.getDetailChallenge(challengeId);
        return responseUtil.successResponse(SuccessCode.CHALLENGE_DETAIL_SUCCESS, response);
    }

    @GetMapping(ApiEndpoints.DOWNLOAD_RESOURCE)
    public ResponseEntity<RestAPIResponse<Object>> downloadResource(@PathVariable("id") Long challengeId) {
        return responseUtil.successResponse(googleDriveServiceImpl.downloadResource(challengeId));
    }
    @GetMapping(ApiEndpoints.DOWNLOAD_ASSETS)
    public ResponseEntity<Resource> downloadAssets(
            @RequestHeader("Authorization") String authorizationHeader,
            @PathVariable(value = "id") String assetsId)
            throws IOException, GeneralSecurityException {

        String token = securityUtil.extractJwtFromHeader(authorizationHeader);
        if (token.isEmpty()) {
            throw new AppException(ErrorCode.INVALID_JWT_TOKEN);
        }

        InputStream fileStream = googleDriveServiceImpl.downloadAssets(assetsId);

        InputStreamResource resource = new InputStreamResource(fileStream);

        String assetsName = resourceRepository.findAssetsNameByAssetsId(assetsId)
                .map(AssetsNameProjection::getAssetsName)
                .orElseThrow(() -> new AppException(ErrorCode.RESOURCE_NOT_EXIST));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + assetsName + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    @GetMapping(ApiEndpoints.DOWNLOAD_FIGMA)
    public ResponseEntity<Resource> downloadFigma(
            @RequestHeader("Authorization") String authorizationHeader,
            @PathVariable(value = "id") String figmaId)
            throws IOException, GeneralSecurityException {

        String token = securityUtil.extractJwtFromHeader(authorizationHeader);
        if (token.isEmpty()) {
            throw new AppException(ErrorCode.INVALID_JWT_TOKEN);
        }

        InputStream fileStream = googleDriveServiceImpl.downloadFigma(figmaId);

        InputStreamResource resource = new InputStreamResource(fileStream);

        String figmaName = resourceRepository.findFigmaNameByFigmaId(figmaId)
                .map(FigmaNameProjection::getFigmaName)
                .orElseThrow(() -> new AppException(ErrorCode.RESOURCE_NOT_EXIST));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + figmaName + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    @PostMapping(ApiEndpoints.SEARCH)
    public ResponseEntity<RestAPIResponse<Object>> searchChallenges(@RequestBody SearchRequest request) {
        ResultPaginationResponse response = challengeService.searchChallenges(request);
        return responseUtil.successResponse(SuccessCode.CHALLENGE_LIST_SUCCESS, response);
    }

    @GetMapping(ApiEndpoints.GET_BY_ID + ApiEndpoints.SOLUTIONS)
    public ResponseEntity<RestAPIResponse<Object>> getAllOtherSolutions() {

        return responseUtil.successResponse(SuccessCode.CHALLENGE_LIST_SUCCESS);
    }
}
