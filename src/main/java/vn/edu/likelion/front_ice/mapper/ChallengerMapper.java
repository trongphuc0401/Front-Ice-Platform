package vn.edu.likelion.front_ice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import vn.edu.likelion.front_ice.dto.request.RegisterRequest;
import vn.edu.likelion.front_ice.dto.response.RegisterResponse;
import vn.edu.likelion.front_ice.dto.response.challenger.ChallengerResponse;
import vn.edu.likelion.front_ice.entity.AccountEntity;
import vn.edu.likelion.front_ice.entity.ChallengeEntity;
import vn.edu.likelion.front_ice.entity.LevelEntity;

@Mapper(componentModel = "spring")
public interface ChallengerMapper {

    ChallengeEntity toAccount(RegisterRequest registerRequest);

    @Mapping(source = "accountEntity.firstName", target = "firstName")
    @Mapping(source = "accountEntity.lastName", target = "lastName")
    @Mapping(source = "accountEntity.email", target = "email")
    @Mapping(source = "accountEntity.role", target = "role")
    @Mapping(source = "accountEntity.id", target = "accountId")
    @Mapping(source = "challengerEntity.levelId", target = "levelId")
    @Mapping(source = "levelEntity.title", target = "level")
    @Mapping(source = "challengerEntity.isPremium", target = "isPremium")
    @Mapping(source = "challengerEntity.urlGithub", target = "urlGithub")
    @Mapping(source = "challengerEntity.score", target = "score")
    ChallengerResponse toChallengerResponse(AccountEntity accountEntity, ChallengerEntity challengerEntity, LevelEntity levelEntity);
}
