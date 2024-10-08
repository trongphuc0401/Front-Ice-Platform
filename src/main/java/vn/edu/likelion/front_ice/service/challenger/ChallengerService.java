package vn.edu.likelion.front_ice.service.challenger;

import vn.edu.likelion.front_ice.dto.request.challenger.UpdateChallengerRequest;
import vn.edu.likelion.front_ice.dto.response.challenger.ChallengerResponse;
import vn.edu.likelion.front_ice.dto.request.FollowRequest;
import vn.edu.likelion.front_ice.dto.response.FollowResponse;
import vn.edu.likelion.front_ice.entity.ChallengerEntity;
import vn.edu.likelion.front_ice.service.BaseService;

import java.util.List;
import java.util.Optional;

/**
 * ChallengerService -
 *
 * @param
 * @return
 * @throws
 */


public interface ChallengerService extends BaseService<ChallengerEntity, UpdateChallengerRequest, ChallengerResponse> {
    Optional<FollowResponse> follow(FollowRequest t);

    Optional<List<RecruiterEntity>> getFollow(String challengerId);
}
