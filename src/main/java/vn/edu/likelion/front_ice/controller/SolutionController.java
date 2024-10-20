package vn.edu.likelion.front_ice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import vn.edu.likelion.front_ice.common.api.ResponseUtil;
import vn.edu.likelion.front_ice.common.api.RestAPIResponse;
import vn.edu.likelion.front_ice.common.constants.ApiEndpoints;
import vn.edu.likelion.front_ice.dto.request.solution.CreateSolutionRequest;
import vn.edu.likelion.front_ice.dto.request.solution.UpdateSolutionRequest;
import vn.edu.likelion.front_ice.dto.response.solution.SolutionResponse;
import vn.edu.likelion.front_ice.mapper.SolutionMapper;
import vn.edu.likelion.front_ice.service.solution.SolutionService;

import java.util.Optional;

/**
 * SolutionController -
 *
 * @param
 * @return
 * @throws
 */
@RestController
@RequestMapping(ApiEndpoints.SOLUTION_API)
@RequiredArgsConstructor
public class SolutionController {

    @Autowired
    private ResponseUtil responseUtil;

    @Autowired
    private SolutionService solutionService;

    @Autowired
    private SolutionMapper solutionMapper;

    @PostMapping
//    @PreAuthorize("hasAuthority('ROLE_CHALLENGER')")
    public ResponseEntity<RestAPIResponse<Object>> create(@RequestBody CreateSolutionRequest createSolutionRequest) {
        Optional<SolutionResponse> response = solutionService.create(createSolutionRequest)
                .map(solutionMapper::toSolutionResponse);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        auth.getAuthorities().forEach(authority -> System.out.println(authority.getAuthority()));

        return responseUtil.successResponse(response);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('ROLE_CHALLENGER')")
    public ResponseEntity<RestAPIResponse<Object>> update(@RequestParam String id,
            @RequestBody UpdateSolutionRequest updateSolutionRequest) {

        return responseUtil.successResponse(solutionService.updateInfo(id, updateSolutionRequest));

    }

}
