package vn.edu.likelion.front_ice.service.handler;

import vn.edu.likelion.front_ice.dto.response.challenge.DetailChallengeResponse;
import vn.edu.likelion.front_ice.entity.ChallengeEntity;

public interface ChallengeAccessHandler {
    void handleAccess(ChallengeEntity challenge, DetailChallengeResponse response);
}
