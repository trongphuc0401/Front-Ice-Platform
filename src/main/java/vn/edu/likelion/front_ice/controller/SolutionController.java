package vn.edu.likelion.front_ice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import vn.edu.likelion.front_ice.common.api.ResponseUtil;
import vn.edu.likelion.front_ice.common.api.RestAPIResponse;
import vn.edu.likelion.front_ice.common.constants.ApiEndpoints;
import vn.edu.likelion.front_ice.dto.request.solution.CreateSolutionRequest;
import vn.edu.likelion.front_ice.dto.request.solution.UpdateSolutionRequest;
import vn.edu.likelion.front_ice.dto.response.solution.SolutionResponse;
import vn.edu.likelion.front_ice.mapper.SolutionMapper;
import vn.edu.likelion.front_ice.service.solution.SolutionService;

import java.io.IOException;
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
    @PreAuthorize("hasAuthority('ROLE_CHALLENGER')")
    @Transactional
    public ResponseEntity<RestAPIResponse<Object>> create(@RequestBody CreateSolutionRequest createSolutionRequest) {

        Optional<SolutionResponse> response = solutionService.create(createSolutionRequest)
                .map(solutionMapper::toSolutionResponse);

        return responseUtil.successResponse(response);
    }

    @PutMapping("/" + ApiEndpoints.ID)
    @PreAuthorize("hasAuthority('ROLE_CHALLENGER')")
    @Transactional
    public ResponseEntity<RestAPIResponse<Object>> update(@PathVariable Long id,
            @RequestBody UpdateSolutionRequest updateSolutionRequest) {

        Optional<SolutionResponse> response = solutionService.updateInfo(id, updateSolutionRequest)
                .map(solutionMapper::toSolutionResponse);

        return responseUtil.successResponse(response);

    }

}
