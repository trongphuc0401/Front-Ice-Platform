package vn.edu.likelion.front_ice.service.handler;

import org.springframework.stereotype.Service;
import vn.edu.likelion.front_ice.common.enums.ChallengeAccessStatus;
import vn.edu.likelion.front_ice.dto.response.challenge.DetailChallengeResponse;
import vn.edu.likelion.front_ice.entity.ChallengeEntity;

@Service
public class PublicAccessHandler  implements ChallengeAccessHandler{
    @Override
    public void handleAccess(ChallengeEntity challenge, DetailChallengeResponse response) {
        response.setAccessStatus(ChallengeAccessStatus.PUBLIC_ACCESS.getStatus());
        response.setAccessMessage(ChallengeAccessStatus.PUBLIC_ACCESS.getMessage());
        response.setResource(null);
        response.setOtherSolutions(null);
    }
}
