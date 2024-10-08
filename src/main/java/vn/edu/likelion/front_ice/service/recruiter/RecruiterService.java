package vn.edu.likelion.front_ice.service.recruiter;

import vn.edu.likelion.front_ice.dto.request.FollowRequest;
import vn.edu.likelion.front_ice.dto.response.FollowResponse;
import vn.edu.likelion.front_ice.dto.response.RecruiterResponse;
import vn.edu.likelion.front_ice.entity.RecruiterEntity;
import vn.edu.likelion.front_ice.service.BaseService;

import java.util.Optional;

public interface RecruiterService extends BaseService<RecruiterEntity, FollowRequest, FollowResponse> {
    Optional<RecruiterResponse> getDetailsProfile(String accountId);
}
