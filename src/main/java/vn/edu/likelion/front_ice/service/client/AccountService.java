package vn.edu.likelion.front_ice.service.client;

import vn.edu.likelion.front_ice.dto.request.LoginRequest;
import vn.edu.likelion.front_ice.dto.response.LoginResponse;
import vn.edu.likelion.front_ice.service.BaseService;

import java.util.Optional;

/**
 * AccountService -
 *
 * @param
 * @return
 * @throws
 */
public interface AccountService extends BaseService {

    Optional<LoginResponse> login(LoginRequest loginRequest);
}
