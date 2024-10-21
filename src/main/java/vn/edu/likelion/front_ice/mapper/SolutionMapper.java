package vn.edu.likelion.front_ice.mapper;

import org.mapstruct.Mapper;
import vn.edu.likelion.front_ice.dto.request.solution.CreateSolutionRequest;
import vn.edu.likelion.front_ice.dto.response.challenge.ChallengeResponse;
import vn.edu.likelion.front_ice.dto.response.solution.SolutionResponse;
import vn.edu.likelion.front_ice.entity.ChallengeEntity;
import vn.edu.likelion.front_ice.entity.SolutionEntity;

@Mapper(componentModel = "spring")
public interface SolutionMapper {

    SolutionEntity toSolution(CreateSolutionRequest createSolutionRequest);

    SolutionResponse toSolutionResponse(SolutionEntity solutionEntity);
}
