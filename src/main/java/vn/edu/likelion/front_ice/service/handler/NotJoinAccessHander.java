package vn.edu.likelion.front_ice.service.handler;

import org.springframework.stereotype.Service;
import vn.edu.likelion.front_ice.common.enums.ChallengeAccessStatus;
import vn.edu.likelion.front_ice.dto.response.challenge.DetailChallengeResponse;
import vn.edu.likelion.front_ice.entity.ChallengeEntity;

@Service
public class NotJoinAccessHander implements ChallengeAccessHandler{
    @Override
    public void handleAccess(ChallengeEntity challenge, DetailChallengeResponse response) {
        response.setAccessStatus(ChallengeAccessStatus.NOT_JOINED.getStatus());
        response.setAccessMessage(ChallengeAccessStatus.NOT_JOINED.getMessage());
        response.setOtherSolutions(null);
    }
}
