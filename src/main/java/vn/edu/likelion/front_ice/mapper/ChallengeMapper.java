package vn.edu.likelion.front_ice.mapper;

import org.mapstruct.Mapper;
import vn.edu.likelion.front_ice.dto.request.challenge.CreateChallengeRequest;
import vn.edu.likelion.front_ice.dto.response.challenge.ChallengeResponse;
import vn.edu.likelion.front_ice.entity.ChallengeEntity;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import vn.edu.likelion.front_ice.dto.request.account.RegisterRequest;
import vn.edu.likelion.front_ice.dto.response.challenge.DetailChallengeResponse;
import vn.edu.likelion.front_ice.dto.response.challenger.ChallengerResponse;
import vn.edu.likelion.front_ice.dto.response.technical.TechnicalResponse;
import vn.edu.likelion.front_ice.entity.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
