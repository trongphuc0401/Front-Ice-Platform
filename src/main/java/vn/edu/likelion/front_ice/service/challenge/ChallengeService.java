package vn.edu.likelion.front_ice.service.challenge;

import vn.edu.likelion.front_ice.dto.request.challenge.CreationChallengeRequest;
import vn.edu.likelion.front_ice.dto.request.challenge.UpdateChallengeRequest;
import vn.edu.likelion.front_ice.dto.response.challenge.ChallengeResponse;
import vn.edu.likelion.front_ice.dto.response.challenge.PaginateChallengeResponse;
import vn.edu.likelion.front_ice.entity.ChallengeEntity;
import vn.edu.likelion.front_ice.service.BaseService;

/**
 * ChallengeService -
 *
 * @param
 * @return
 * @throws
 */


public interface ChallengeService extends BaseService<ChallengeEntity, CreationChallengeRequest, UpdateChallengeRequest> {
    PaginateChallengeResponse getPaginationChallengeByCategory(String category, int pageNo, int pagSize);
}
