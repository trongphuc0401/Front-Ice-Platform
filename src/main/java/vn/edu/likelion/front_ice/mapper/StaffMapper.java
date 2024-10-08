package vn.edu.likelion.front_ice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import vn.edu.likelion.front_ice.dto.request.RegisterRequest;
import vn.edu.likelion.front_ice.dto.response.ChallengerResponse;
import vn.edu.likelion.front_ice.dto.response.RegisterResponse;
import vn.edu.likelion.front_ice.dto.response.StaffResponse;
import vn.edu.likelion.front_ice.entity.AccountEntity;
import vn.edu.likelion.front_ice.entity.ChallengeEntity;
import vn.edu.likelion.front_ice.entity.ChallengerEntity;
import vn.edu.likelion.front_ice.entity.StaffEntity;

@Mapper(componentModel = "spring")
public interface StaffMapper {

//    ChallengeEntity toAccount(RegisterRequest registerRequest);

    @Mapping(source = "accountEntity.firstName", target = "firstName")
    @Mapping(source = "accountEntity.lastName", target = "lastName")
    @Mapping(source = "accountEntity.email", target = "email")
    @Mapping(source = "accountEntity.role", target = "role")
    @Mapping(source = "accountEntity.id", target = "accountId")
    @Mapping(source = "staffEntity.nameStaff", target = "nameStaff")
    StaffResponse toStaffResponse(AccountEntity accountEntity, StaffEntity staffEntity);

}
