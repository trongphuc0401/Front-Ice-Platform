package vn.edu.likelion.front_ice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import vn.edu.likelion.front_ice.dto.request.solution.CreateSolutionRequest;
import vn.edu.likelion.front_ice.dto.request.solution.UpdateSolutionRequest;
import vn.edu.likelion.front_ice.dto.response.challenge.ChallengeResponse;
import vn.edu.likelion.front_ice.dto.response.solution.SolutionResponse;
import vn.edu.likelion.front_ice.entity.ChallengeEntity;
import vn.edu.likelion.front_ice.entity.SolutionEntity;

@Mapper(componentModel = "spring")
public interface SolutionMapper {

    SolutionEntity toSolution(CreateSolutionRequest createSolutionRequest);

    @Mapping(target = "challenger", source = "challenger.id")
    @Mapping(target = "challenge", source = "challenge.id")
    SolutionResponse toSolutionResponse(SolutionEntity solutionEntity);

    SolutionEntity toSolutionUpdate(UpdateSolutionRequest request, @MappingTarget SolutionEntity solutionEntity);
}
