package vn.edu.likelion.front_ice.service.client;

import org.springframework.stereotype.Service;
import vn.edu.likelion.front_ice.dto.request.LoginRequest;
import vn.edu.likelion.front_ice.dto.request.RegisterRequest;
import vn.edu.likelion.front_ice.dto.response.LoginResponse;
import vn.edu.likelion.front_ice.dto.response.RegisterResponse;
import vn.edu.likelion.front_ice.entity.AccountEntity;
import vn.edu.likelion.front_ice.service.BaseService;

import java.util.Optional;

/**
 * AccountService -
 *
 * @param
 * @return
 * @throws
 */

public interface AccountService extends BaseService<AccountEntity,RegisterRequest, RegisterResponse> {


    Optional<LoginResponse> login(LoginRequest loginRequest);

    String generateOTP(String email);

    boolean verifyOTP(String email, String otp);

    void clearOTP(String email);
}
