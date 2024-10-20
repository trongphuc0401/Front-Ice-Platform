package vn.edu.likelion.front_ice.mapper;

import org.mapstruct.Mapper;
import vn.edu.likelion.front_ice.dto.request.challenge.CreateChallengeRequest;
import vn.edu.likelion.front_ice.dto.response.challenge.ChallengeResponse;
import vn.edu.likelion.front_ice.entity.ChallengeEntity;

@Mapper(componentModel = "spring")
public interface ChallengeMapper {

    ChallengeEntity toChallenge(CreateChallengeRequest createChallengeRequest);

    ChallengeResponse toChallengeResponse(ChallengeEntity challengeEntity);
}
