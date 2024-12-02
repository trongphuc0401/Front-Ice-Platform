package vn.edu.likelion.front_ice.service.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.edu.likelion.front_ice.common.enums.ChallengeAccessStatus;
import vn.edu.likelion.front_ice.dto.response.challenge.DetailChallengeResponse;
import vn.edu.likelion.front_ice.dto.response.solution.OtherSolutionResponse;
import vn.edu.likelion.front_ice.entity.ChallengeEntity;
import vn.edu.likelion.front_ice.entity.LevelEntity;
import vn.edu.likelion.front_ice.entity.SolutionEntity;
import vn.edu.likelion.front_ice.mapper.ResourceMapper;
import vn.edu.likelion.front_ice.mapper.SolutionMapper;
import vn.edu.likelion.front_ice.repository.LevelRepository;
import vn.edu.likelion.front_ice.repository.SolutionRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SubmittedAccessHandler implements ChallengeAccessHandler {

    @Autowired
    private SolutionRepository solutionRepository;


    @Autowired
    private LevelRepository levelRepository;

    @Autowired
    private ResourceMapper resourceMapper;

    @Autowired
    private SolutionMapper solutionMapper;

    public void handleAccess(ChallengeEntity challenge, DetailChallengeResponse response) {
        response.setAccessStatus(ChallengeAccessStatus.SUBMITTED.getStatus());
        response.setAccessMessage(ChallengeAccessStatus.SUBMITTED.getMessage());

        List<SolutionEntity> solutions = solutionRepository.findLimitedOtherSolutions(challenge.getId());

        Map<Long, LevelEntity> levelMap = loadLevelEntities(solutions);

        List<OtherSolutionResponse> otherSolutions = solutions.stream()
                .map(solution -> {
                    OtherSolutionResponse responseItem = solutionMapper.toOtherSolutionResponse(solution);
                    LevelEntity levelEntity = levelMap.get(solution.getChallenger().getLevelId());
                    responseItem.setChallengerLevel(levelEntity != null ? levelEntity.getLevel() : null);
                    return responseItem;
                })
                .collect(Collectors.toList());

        response.setOtherSolutions(otherSolutions);
    }

    private Map<Long, LevelEntity> loadLevelEntities(List<SolutionEntity> solutions) {
        List<Long> levelIds = solutions.stream()
                .map(solution -> solution.getChallenger().getLevelId())
                .distinct()  // Loại bỏ trùng lặp
                .collect(Collectors.toList());

        return levelRepository.findLevelsByIds(levelIds).stream()
                .collect(Collectors.toMap(LevelEntity::getId, level -> level));
    }
}
