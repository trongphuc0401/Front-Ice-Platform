package vn.edu.likelion.front_ice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import vn.edu.likelion.front_ice.dto.request.RegisterRequest;
import vn.edu.likelion.front_ice.dto.response.RecruiterResponse;
import vn.edu.likelion.front_ice.dto.response.RegisterResponse;
import vn.edu.likelion.front_ice.entity.AccountEntity;
import vn.edu.likelion.front_ice.entity.RecruiterEntity;

@Mapper(componentModel = "spring")
public interface RecruiterMapper {

//    AccountEntity toAccount(RegisterRequest registerRequest);

    @Mapping(source = "accountEntity.firstName", target = "firstName")
    @Mapping(source = "accountEntity.lastName", target = "lastName")
    @Mapping(source = "accountEntity.email", target = "email")
    @Mapping(source = "accountEntity.role", target = "role")
    @Mapping(source = "accountEntity.id", target = "accountId")
    @Mapping(source = "recruiterEntity.name", target = "name")
    @Mapping(source = "recruiterEntity.description", target = "description")
    @Mapping(source = "recruiterEntity.urlWebsite", target = "urlWebsite")
    @Mapping(source = "recruiterEntity.address", target = "address")
    RecruiterResponse toRecruiterResponse(AccountEntity accountEntity, RecruiterEntity recruiterEntity);

}
