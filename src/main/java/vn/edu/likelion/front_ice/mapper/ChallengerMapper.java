package vn.edu.likelion.front_ice.mapper;

import org.mapstruct.Mapper;
import vn.edu.likelion.front_ice.dto.request.RegisterRequest;
import vn.edu.likelion.front_ice.dto.request.challenger.UpdateChallengerRequest;
import vn.edu.likelion.front_ice.dto.response.RegisterResponse;
import vn.edu.likelion.front_ice.dto.response.challenger.ChallengerResponse;
import vn.edu.likelion.front_ice.entity.AccountEntity;
import vn.edu.likelion.front_ice.entity.ChallengeEntity;

@Mapper(componentModel = "spring")
public interface ChallengerMapper {

    ChallengeEntity toAccount(UpdateChallengerRequest registerRequest);

    ChallengerResponse toRegisterResponse(AccountEntity accountEntity);

}
