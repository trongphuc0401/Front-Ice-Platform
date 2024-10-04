package vn.edu.likelion.front_ice.service.client;

import vn.edu.likelion.front_ice.dto.request.RegisterRequest;
import vn.edu.likelion.front_ice.dto.response.RegisterResponse;
import vn.edu.likelion.front_ice.entity.AccountEntity;
import vn.edu.likelion.front_ice.service.BaseService;

public interface AccountService extends BaseService<AccountEntity, RegisterRequest, RegisterResponse> {

}
