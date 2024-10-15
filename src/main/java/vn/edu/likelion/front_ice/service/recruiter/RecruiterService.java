package vn.edu.likelion.front_ice.service.recruiter;

import vn.edu.likelion.front_ice.dto.request.follow.FollowRequest;
import vn.edu.likelion.front_ice.dto.response.follow.FollowResponse;
import vn.edu.likelion.front_ice.dto.response.recruiter.RecruiterResponse;
import vn.edu.likelion.front_ice.entity.RecruiterEntity;
import vn.edu.likelion.front_ice.service.BaseService;

import java.util.Optional;

public interface RecruiterService extends BaseService<RecruiterEntity, FollowRequest, FollowRequest> {
    Optional<RecruiterResponse> getDetailsProfile(String accountId);
}
