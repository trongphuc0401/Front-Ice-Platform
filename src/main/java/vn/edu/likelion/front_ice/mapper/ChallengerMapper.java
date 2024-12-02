package vn.edu.likelion.front_ice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import vn.edu.likelion.front_ice.common.enums.Level;
import vn.edu.likelion.front_ice.dto.request.account.RegisterRequest;
import vn.edu.likelion.front_ice.dto.response.challenger.ChallengerResponse;
import vn.edu.likelion.front_ice.dto.response.challenger.OverviewResponse;
import vn.edu.likelion.front_ice.entity.AccountEntity;
import vn.edu.likelion.front_ice.entity.ChallengeEntity;
import vn.edu.likelion.front_ice.entity.ChallengerEntity;
import vn.edu.likelion.front_ice.entity.LevelEntity;
import vn.edu.likelion.front_ice.projection.challenger.OverviewProjection;

@Mapper(componentModel = "spring")
public interface ChallengerMapper {

    ChallengeEntity toAccount(RegisterRequest registerRequest);

    ChallengerResponse toChallengerResponse(AccountEntity accountEntity, ChallengerEntity challengerEntity, LevelEntity levelEntity);

    OverviewResponse toOverviewResponse(OverviewProjection overviewProjection,LevelEntity level);
}
