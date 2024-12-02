package vn.edu.likelion.front_ice.service.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.likelion.front_ice.common.enums.ChallengeAccessStatus;
import vn.edu.likelion.front_ice.dto.response.challenge.DetailChallengeResponse;
import vn.edu.likelion.front_ice.dto.response.solution.OtherSolutionResponse;
import vn.edu.likelion.front_ice.entity.ChallengeEntity;
import vn.edu.likelion.front_ice.mapper.ResourceMapper;
import vn.edu.likelion.front_ice.repository.SolutionRepository;

import java.util.List;

@Service
public class SubmittedAccessHandler implements ChallengeAccessHandler {

    @Autowired
    private SolutionRepository solutionRepository;

    @Autowired
    private ResourceMapper resourceMapper;

    @Override
    public void handleAccess(ChallengeEntity challenge, DetailChallengeResponse response) {
        response.setAccessStatus(ChallengeAccessStatus.SUBMITTED.getStatus());
        response.setAccessMessage(ChallengeAccessStatus.SUBMITTED.getMessage());

        List<OtherSolutionResponse> otherSolutions = solutionRepository.findOtherSolutions(challenge.getId());
        response.setOtherSolutions(otherSolutions);
    }
}
