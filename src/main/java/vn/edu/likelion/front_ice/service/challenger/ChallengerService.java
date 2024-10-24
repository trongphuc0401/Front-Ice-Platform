package vn.edu.likelion.front_ice.service.challenger;

import vn.edu.likelion.front_ice.dto.request.challenger.CreateChallengerRequest;
import vn.edu.likelion.front_ice.dto.request.challenger.UpdateChallengerRequest;
import vn.edu.likelion.front_ice.dto.response.challenger.ChallengerResponse;
import vn.edu.likelion.front_ice.dto.request.follow.FollowRequest;
import vn.edu.likelion.front_ice.dto.response.follow.FollowResponse;
import vn.edu.likelion.front_ice.entity.ChallengerEntity;
import vn.edu.likelion.front_ice.entity.RecruiterEntity;
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


public interface ChallengerService extends BaseService<ChallengerEntity, CreateChallengerRequest, UpdateChallengerRequest> {
    Optional<FollowResponse> follow(FollowRequest t);

    Optional<List<RecruiterEntity>> getFollow(String challengerId);

    Optional<ChallengerResponse> getDetailsProfile(String accessToken);
}
