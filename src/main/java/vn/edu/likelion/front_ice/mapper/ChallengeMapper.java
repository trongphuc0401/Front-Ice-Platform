package vn.edu.likelion.front_ice.mapper;

import org.mapstruct.Mapper;
import vn.edu.likelion.front_ice.dto.request.challenge.CreateChallengeRequest;
import vn.edu.likelion.front_ice.dto.response.challenge.ChallengeResponse;
import vn.edu.likelion.front_ice.entity.ChallengeEntity;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import vn.edu.likelion.front_ice.dto.response.challenge.DetailChallengeResponse;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Mapper(componentModel = "spring")
public interface ChallengeMapper {

    ChallengeEntity toChallenge(CreateChallengeRequest createChallengeRequest);

    @Mapping(target = "technicals", source = "technicals")
    @Mapping(target = "createAt", source = "createAt", qualifiedByName = "toTimestamp")
    @Mapping(target = "updateAt", source = "updateAt", qualifiedByName = "toTimestamp")
    ChallengeResponse toChallengeResponse(ChallengeEntity challengeEntity);

    DetailChallengeResponse toDetailChallengeResponse(ChallengeEntity challengeEntity);

    @Named("toTimestamp")
    default Long toTimestamp(LocalDateTime localDateTime) {
        return (localDateTime != null) ? localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() : null;
    }
}
