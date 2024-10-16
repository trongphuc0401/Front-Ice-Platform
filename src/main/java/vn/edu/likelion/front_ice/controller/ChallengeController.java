package vn.edu.likelion.front_ice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
import vn.edu.likelion.front_ice.service.challenge.ChallengeService;

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

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_RECRUITER', 'ROLE_MANAGER', 'ROLE_ADMIN')")
    public ResponseEntity<RestAPIResponse<Object>> getChallengeByCategory(@RequestParam String category
            , @RequestParam int sizeNo, @RequestParam int pageNo) {
        return responseUtil.successResponse(challengeService
                .getPaginationChallengeByCategory(category, sizeNo, pageNo));
    }

    @GetMapping(ApiEndpoints.DOWNLOAD_ASSETS)
    public ResponseEntity<RestAPIResponse<Object>> downloadAssets(@PathVariable("id") String challengeId) {
        ResourceEntity resource = resourceRepository.findByChallengeId(challengeId)
                .orElseThrow(() ->new AppException(ErrorCode.CHALLENGE_NOT_EXIST));



        return responseUtil.successResponse(ErrorCode.DOWNLOAD_SUCCESSFUL);
    }


}
