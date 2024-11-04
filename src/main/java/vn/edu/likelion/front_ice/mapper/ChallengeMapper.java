package vn.edu.likelion.front_ice.mapper;

import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import vn.edu.likelion.front_ice.dto.request.challenge.CreateChallengeRequest;
import vn.edu.likelion.front_ice.dto.response.challenge.ChallengeDetailForChallengerResponse;
import vn.edu.likelion.front_ice.dto.response.challenge.ChallengeResponse;
import vn.edu.likelion.front_ice.entity.ChallengeEntity;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import vn.edu.likelion.front_ice.dto.response.challenge.DetailChallengeResponse;
import vn.edu.likelion.front_ice.entity.ResourceEntity;
import vn.edu.likelion.front_ice.entity.TechnicalEntity;
import vn.edu.likelion.front_ice.repository.ResourceRepository;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ChallengeMapper {


    ChallengeEntity toChallenge(CreateChallengeRequest createChallengeRequest);

    @Mapping(target = "technicals", source = "technicals", qualifiedByName = "mapTechnicals")
    @Mapping(target = "createAt", source = "createAt", qualifiedByName = "toTimestamp")
    @Mapping(target = "updateAt", source = "updateAt", qualifiedByName = "toTimestamp")
    ChallengeResponse toChallengeResponse(ChallengeEntity challengeEntity);

    @Mapping(target = "technicals", source = "technicals", qualifiedByName = "mapTechnicals")
    ChallengeDetailForChallengerResponse toChallengeDetailResponse(ChallengeEntity challengeEntity);

    @Named("toTimestamp")
    default Long toTimestamp(LocalDateTime localDateTime) {
        return (localDateTime != null) ? localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() : null;
    }

    @Named("mapTechnicals")
    default Set<String> mapTechnicals(Set<TechnicalEntity> technicals){
        return technicals.stream()
                .map(TechnicalEntity::getTitle)
                .collect(Collectors.toSet());
    }

}
