package vn.edu.likelion.front_ice.service.challenge;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.edu.likelion.front_ice.common.query.SearchRequest;
import vn.edu.likelion.front_ice.dto.request.challenge.CreateChallengeRequest;
import vn.edu.likelion.front_ice.dto.request.challenge.UpdateChallengeRequest;
import vn.edu.likelion.front_ice.dto.response.challenge.PaginateChallengeResponse;
import vn.edu.likelion.front_ice.dto.response.challenge.ResultPaginationResponse;
import vn.edu.likelion.front_ice.entity.ChallengeEntity;
import vn.edu.likelion.front_ice.service.BaseService;

/**
 * ChallengeService -
 *
 * @param
 * @return
 * @throws
 */


public interface ChallengeService extends BaseService<ChallengeEntity, CreateChallengeRequest, UpdateChallengeRequest> {
    PaginateChallengeResponse getPaginationChallengeByCategory(String category, int pageNo, int pagSize);

    ResultPaginationResponse getPaginationChallenge(int pageNo, int pageSize);

    ResultPaginationResponse searchChallenges(SearchRequest request);
}
