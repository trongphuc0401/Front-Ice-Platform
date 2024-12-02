package vn.edu.likelion.front_ice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import vn.edu.likelion.front_ice.common.enums.Level;
import vn.edu.likelion.front_ice.dto.request.solution.CreateSolutionRequest;
import vn.edu.likelion.front_ice.dto.request.solution.UpdateSolutionRequest;
import vn.edu.likelion.front_ice.dto.response.solution.OtherSolutionChallengerResponse;
import vn.edu.likelion.front_ice.dto.response.solution.OtherSolutionResponse;
import vn.edu.likelion.front_ice.dto.response.solution.SolutionResponse;
import vn.edu.likelion.front_ice.entity.LevelEntity;
import vn.edu.likelion.front_ice.entity.SolutionEntity;
import vn.edu.likelion.front_ice.repository.LevelRepository;

@Mapper(componentModel = "spring")
public interface SolutionMapper {


    SolutionEntity toSolution(CreateSolutionRequest createSolutionRequest);

    @Mapping(target = "challenger", source = "challenger.id")
    @Mapping(target = "challenge", source = "challenge.id")
    SolutionResponse toSolutionResponse(SolutionEntity solutionEntity);

    SolutionEntity toSolutionUpdate(UpdateSolutionRequest request, @MappingTarget SolutionEntity solutionEntity);

    @Mapping(target = "challengerFirstName", source = "challenger.account.firstName")
    @Mapping(target = "challengerLastName", source = "challenger.account.lastName")
    @Mapping(target = "challengerAvatar", source = "challenger.account.avatar")
    OtherSolutionResponse toOtherSolutionResponse(SolutionEntity solution);

    @Mapping(target = "challengerFirstName", source = "challenger.account.firstName")
    @Mapping(target = "challengerLastName", source = "challenger.account.lastName")
    @Mapping(target = "challengerAvatar", source = "challenger.account.avatar")
    @Mapping(target = "challengePoint", source = "challenge.challengePoint")
    OtherSolutionChallengerResponse toOtherSolutionChallengerResponse(SolutionEntity solution);
}
