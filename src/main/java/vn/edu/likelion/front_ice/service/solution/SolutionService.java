package vn.edu.likelion.front_ice.service.solution;

import vn.edu.likelion.front_ice.dto.request.solution.CreateSolutionRequest;
import vn.edu.likelion.front_ice.dto.request.solution.UpdateSolutionRequest;
import vn.edu.likelion.front_ice.dto.response.challenge.ResultPaginationResponse;
import vn.edu.likelion.front_ice.entity.SolutionEntity;
import vn.edu.likelion.front_ice.service.BaseService;

/**
 * SolutionService -
 *
 * @param
 * @return
 * @throws
 */


public interface SolutionService extends BaseService<SolutionEntity, CreateSolutionRequest, UpdateSolutionRequest> {

    ResultPaginationResponse getPaginationChallengerSolution(int pageNo, int pageSize);

}
