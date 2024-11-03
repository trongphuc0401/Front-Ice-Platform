package vn.edu.likelion.front_ice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import vn.edu.likelion.front_ice.dto.request.solution.CreateSolutionRequest;
import vn.edu.likelion.front_ice.dto.request.solution.UpdateSolutionRequest;
import vn.edu.likelion.front_ice.dto.response.challenge.ChallengeResponse;
import vn.edu.likelion.front_ice.dto.response.solution.SolutionChallengerResponse;
import vn.edu.likelion.front_ice.dto.response.solution.SolutionResponse;
import vn.edu.likelion.front_ice.dto.response.technical.TechnicalResponse;
import vn.edu.likelion.front_ice.entity.ChallengeEntity;
import vn.edu.likelion.front_ice.entity.SolutionEntity;
import vn.edu.likelion.front_ice.entity.TechnicalEntity;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface SolutionMapper {

    SolutionMapper INSTANCE = Mappers.getMapper(SolutionMapper.class);

    SolutionEntity toSolution(CreateSolutionRequest createSolutionRequest);

    @Mapping(target = "challenger", source = "challenger.id")
    @Mapping(target = "challenge", source = "challenge.id")
    SolutionResponse toSolutionResponse(SolutionEntity solutionEntity);

    @Mapping(source = "challenge.banner", target = "banner")
    @Mapping(source = "createAt", target = "submittedAt",qualifiedByName = "mapCreatedAtToTimestamp")
    @Mapping(source = "title", target = "title")
    @Mapping(source = "challenge.technicals", target = "technicals")
    @Mapping(source = "challenge.challengePoint", target = "challengePoint")
    SolutionChallengerResponse toSolutionChallengerResponse(SolutionEntity solutionEntity);

    SolutionEntity toSolutionUpdate(UpdateSolutionRequest request, @MappingTarget SolutionEntity solutionEntity);

    @Named("mapCreatedAtToTimestamp")
    default Long mapCreatedAtToTimestamp(LocalDateTime createdAt) {
        if (createdAt == null) {
            return null;
        }
        // Chuyển LocalDateTime sang Long (timestamp)
        return createdAt.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    @Named("mapTechnicalsFromChallenge")
    default Set<TechnicalEntity> mapTechnicalsFromChallenge(vn.edu.likelion.front_ice.entity.ChallengeEntity challenge) {
        if (challenge == null) {
            return null;
        }
        return challenge.getTechnicals(); // Giả sử ChallengeEntity có phương thức getTechnicals() trả về Set<TechnicalResponse>
    }
}
