package vn.edu.likelion.front_ice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import vn.edu.likelion.front_ice.common.exceptions.SuccessCode;
import vn.edu.likelion.front_ice.common.query.SearchRequest;
import vn.edu.likelion.front_ice.common.utils.PaginationUtil;
import vn.edu.likelion.front_ice.dto.request.challenge.CreationChallengeRequest;
import vn.edu.likelion.front_ice.dto.response.challenge.ChallengeResponse;
import vn.edu.likelion.front_ice.dto.response.challenge.DetailChallengeResponse;
import vn.edu.likelion.front_ice.dto.response.challenge.ResultPaginationResponse;
import vn.edu.likelion.front_ice.common.exceptions.AppException;
import vn.edu.likelion.front_ice.common.exceptions.ErrorCode;
import vn.edu.likelion.front_ice.entity.ChallengeEntity;
import vn.edu.likelion.front_ice.entity.ResourceEntity;
import vn.edu.likelion.front_ice.mapper.ChallengeMapper;
import vn.edu.likelion.front_ice.repository.ResourceRepository;
import vn.edu.likelion.front_ice.security.SecurityUtil;
import vn.edu.likelion.front_ice.service.challenge.ChallengeService;
import vn.edu.likelion.front_ice.service.gdrive.GoogleDriveServiceImpl;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

//    @GetMapping
//    @PreAuthorize("hasAnyAuthority('ROLE_RECRUITER', 'ROLE_MANAGER', 'ROLE_ADMIN')")
//    public ResponseEntity<RestAPIResponse<Object>> getChallengeByCategory(@RequestParam String category
//            , @RequestParam int sizeNo, @RequestParam int pageNo) {
//        return responseUtil.successResponse(challengeService
//                .getPaginationChallengeByCategory(category, sizeNo, pageNo));
//    }

    @GetMapping
    public ResponseEntity<RestAPIResponse<Object>> getAllChallenges(
            @RequestParam(defaultValue = "1") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize) {

        ResultPaginationResponse response = challengeService.getPaginationChallenge(pageNo, pageSize);
        return responseUtil.successResponse(SuccessCode.CHALLENGE_LIST_SUCCESS, response);
    }

    @GetMapping(ApiEndpoints.GET_CHALLENGE_DETAIL)
    public ResponseEntity<RestAPIResponse<Object>> getChallengeDetail(@PathVariable("id") String id) {
        ChallengeEntity challengeEntity = challengeService.findById(id);
        DetailChallengeResponse challengeResponse = challengeMapper.toDetailChallengeResponse(challengeEntity);
        return responseUtil.successResponse(SuccessCode.CHALLENGE_DETAIL_SUCCESS, challengeResponse);
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

    @GetMapping(ApiEndpoints.DOWNLOAD_FIGMA)
    public ResponseEntity<Resource> downloadFigma(
            @RequestHeader("Authorization") String authorizationHeader,
            @PathVariable(value = "id") String challengeId)
            throws IOException, GeneralSecurityException {

        String token = securityUtil.extractJwtFromHeader(authorizationHeader);
        if (token.isEmpty()) {
            throw new AppException(ErrorCode.INVALID_JWT_TOKEN);
        }

        InputStream fileStream = googleDriveServiceImpl.downloadFigma(challengeId);

        InputStreamResource resource = new InputStreamResource(fileStream);

        ResourceEntity resourceEntity = resourceRepository.findByChallengeId(challengeId)
                .orElseThrow(() -> new AppException(ErrorCode.CHALLENGE_NOT_EXIST));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resourceEntity.getFigmaName() + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    @PostMapping(ApiEndpoints.SEARCH)
    public ResponseEntity<RestAPIResponse<Object>> searchChallenges(@RequestBody SearchRequest request) {
        ResultPaginationResponse response = challengeService.searchChallenges(request);
        return responseUtil.successResponse(SuccessCode.CHALLENGE_LIST_SUCCESS, response);
    }

    @PostMapping("/create")
    public ResponseEntity<RestAPIResponse<Object>> createChallenge(@RequestBody CreationChallengeRequest request) {
        Optional<ChallengeEntity> challengeEntity = challengeService.create(request);

        if (challengeEntity.isPresent()) {
            return responseUtil.successResponse(SuccessCode.CHALLENGE_LIST_SUCCESS, challengeEntity.get());
        } else {
            throw new AppException(ErrorCode.CHALLENGE_NOT_EXIST);
        }
    }
}
