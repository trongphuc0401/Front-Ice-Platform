package vn.edu.likelion.front_ice.service.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.likelion.front_ice.common.enums.ChallengeAccessStatus;
import vn.edu.likelion.front_ice.dto.response.challenge.DetailChallengeResponse;
import vn.edu.likelion.front_ice.entity.ChallengeEntity;
import vn.edu.likelion.front_ice.mapper.ResourceMapper;

@Service
public class JoinedAccessHandler implements ChallengeAccessHandler {

    @Autowired
    private ResourceMapper resourceMapper;

    @Override
    public void handleAccess(ChallengeEntity challenge, DetailChallengeResponse response) {
        response.setAccessStatus(ChallengeAccessStatus.JOINED.getStatus());
        response.setAccessMessage(ChallengeAccessStatus.JOINED.getMessage());
        response.setResource(resourceMapper.toResourceResponse(challenge.getResource()));
        response.setOtherSolutions(null);
    }
}
