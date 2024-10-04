package vn.edu.likelion.front_ice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.likelion.front_ice.common.api.ResponseUtil;
import vn.edu.likelion.front_ice.common.api.RestAPIResponse;
import vn.edu.likelion.front_ice.common.constants.ApiEndpoints;
import vn.edu.likelion.front_ice.dto.request.LoginRequest;
import vn.edu.likelion.front_ice.dto.request.RegisterRequest;
import vn.edu.likelion.front_ice.dto.response.RegisterResponse;
import vn.edu.likelion.front_ice.security.SecurityUtil;
import vn.edu.likelion.front_ice.service.client.AccountService;

import java.util.Optional;

/**
 * AccountController -
 *
 * @param
 * @return
 * @throws
 */
@RestController
@RequestMapping(ApiEndpoints.AUTH_API)
@RequiredArgsConstructor
public class AccountController {
    @Autowired
    ResponseUtil responseUtil;

    @Autowired
    AccountService accountService;

    @Autowired SecurityUtil securityUtil;

    @PostMapping(ApiEndpoints.SIGN_UP)
    public Optional<RegisterResponse> register(@RequestBody RegisterRequest registerRequest) {

        return accountService.create(registerRequest);
    }

    @PostMapping(ApiEndpoints.LOGIN)
    public ResponseEntity<RestAPIResponse<Object>> login(@RequestBody LoginRequest loginRequest) {
        String refreshToken = this.securityUtil.createRefreshToken(loginRequest.getEmail(), loginRequest);

        // set cookies
        ResponseCookie resCookies = ResponseCookie
                .from("refresh_token", refreshToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(securityUtil.getRefreshTokenExpiration())
                .build();

        return responseUtil.successResponse(accountService.login(loginRequest),resCookies.toString());
    }

}
