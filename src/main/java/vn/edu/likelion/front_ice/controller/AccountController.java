package vn.edu.likelion.front_ice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import vn.edu.likelion.front_ice.common.api.ResponseUtil;
import vn.edu.likelion.front_ice.common.api.RestAPIResponse;
import vn.edu.likelion.front_ice.common.constants.ApiEndpoints;
import vn.edu.likelion.front_ice.common.exceptions.AppException;
import vn.edu.likelion.front_ice.common.exceptions.ErrorCode;
import vn.edu.likelion.front_ice.common.exceptions.SuccessCode;
import vn.edu.likelion.front_ice.dto.request.account.*;
import vn.edu.likelion.front_ice.dto.response.account.LoginResponse;
import vn.edu.likelion.front_ice.dto.response.account.RefreshTokenResponse;
import vn.edu.likelion.front_ice.entity.AccountEntity;
import vn.edu.likelion.front_ice.mapper.AccountMapper;
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

    @Autowired
    SecurityUtil securityUtil;
    @Autowired
    private AccountMapper accountMapper;

    @PostMapping(ApiEndpoints.SIGN_UP)
    @Transactional
    public ResponseEntity<RestAPIResponse<Object>> register(@RequestBody RegisterRequest registerRequest) {
        Optional<AccountEntity> entity = (accountService.create(registerRequest));
        return responseUtil.successResponse(accountMapper.toRegisterResponse(
                entity.orElseThrow(() -> new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION)))
        );
    }

    @PostMapping(ApiEndpoints.LOGIN)
    public ResponseEntity<RestAPIResponse<Object>> login(@RequestBody LoginRequest loginRequest) {

        return responseUtil.successResponse(accountService.login(loginRequest));
    }

    @PostMapping(ApiEndpoints.SEND_OTP)
    public ResponseEntity<RestAPIResponse<Object>> sendOTP(@RequestParam String gmail) {
        accountService.generateOTP(gmail);
        return responseUtil.successResponse(SuccessCode.SENT_OTP_EMAIL);
    }

    @PostMapping(ApiEndpoints.VERIFY_EMAIL)
    public ResponseEntity<RestAPIResponse<Object>> verifyEmail(@RequestBody VerifyEmailRequest verifyEmailRequest) {
        boolean flag = accountService.verifyOTP(verifyEmailRequest.getEmail(), verifyEmailRequest.getOtp());

        if (flag) {
            accountService.clearOTP(verifyEmailRequest.getEmail());
            return responseUtil.successResponse(SuccessCode.VERIFIED);
        } else {
            throw new AppException(ErrorCode.OTP_INVALID);
        }
    }


    @PostMapping(ApiEndpoints.FORGOT_PASSWORD)
    public ResponseEntity<RestAPIResponse<Object>> forgotPassword(@RequestBody
                                                                  ForgotPasswordRequest forgotPasswordRequest) {
        Optional<AccountEntity> accountOptional = accountService.findByEmail(forgotPasswordRequest.getEmail());
        if (accountOptional.isEmpty()) {
            throw new AppException(ErrorCode.ACCOUNT_NOT_EXIST);
        }

         accountService.generateForgotPasswordOTP(forgotPasswordRequest.getEmail());
        return responseUtil.successResponse(SuccessCode.SENT_OTP_EMAIL);
    }

    @PostMapping(ApiEndpoints.VERIFY_FORGOT_PASSWORD_OTP)
    public ResponseEntity<RestAPIResponse<Object>> verifyForgotPasswordOTP(@RequestBody
                                                                           VerifyForgotPasswordOTPRequest verifyOtpRequest) {
        boolean isOtpValid = accountService.verifyForgotPasswordOTP(verifyOtpRequest.getEmail(), verifyOtpRequest.getOtp());

        if (isOtpValid) {
            String resetToken = accountService.generateResetToken(verifyOtpRequest.getEmail());
            return responseUtil.successResponse(resetToken);
        } else {
            throw new AppException(ErrorCode.OTP_INVALID);
        }
    }

    @PostMapping(ApiEndpoints.RESET_PASSWORD)
    public ResponseEntity<RestAPIResponse<Object>> resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) {
        if (!resetPasswordRequest.getNewPassword().equals(resetPasswordRequest.getConfirmPassword())) {
            throw new AppException(ErrorCode.CONFIRM_PASSWORD_NOT_MATCH);
        }

        boolean isPasswordReset = accountService.resetPassword(resetPasswordRequest.getResetToken(), resetPasswordRequest.getNewPassword());

        if (isPasswordReset) {
            return responseUtil.successResponse(SuccessCode.PASSWORD_CHANGED);
        } else {
            throw new AppException(ErrorCode.PASSWORD_RESET_FAILED);
        }
    }

    @GetMapping(ApiEndpoints.REFRESH_TOKEN)
    public ResponseEntity<RestAPIResponse<Object>> refreshToken(
            @CookieValue(name = "refresh_token", defaultValue = "a") String refresh_token) {
        if ("a".equals(refresh_token)) {
            throw new AppException(ErrorCode.INVALID_REFRESH_TOKEN);
        }

        Optional<RefreshTokenResponse> refreshTokenResponse = accountService.refreshToken(refresh_token);

        // Create the new refresh token cookie
        ResponseCookie refreshCookie = ResponseCookie
                .from("refresh_token", refreshTokenResponse.get().getAccessToken())
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(securityUtil.getRefreshTokenExpiration())
                .build();

        return responseUtil.successResponse(refreshTokenResponse, refreshCookie.toString());
    }

    @PostMapping(ApiEndpoints.REFRESH_TOKEN)
    public ResponseEntity<RestAPIResponse<Object>> handleRefreshToken(
            @RequestBody RefreshTokenRequest refreshTokenRequest) {

        String refreshToken = refreshTokenRequest.getRefreshToken();
        if (refreshToken == null || refreshToken.isEmpty()) {
            throw new AppException(ErrorCode.INVALID_REFRESH_TOKEN);
        }

        RefreshTokenResponse refreshTokenResponse = accountService.refreshToken(refreshToken)
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_REFRESH_TOKEN));

        return responseUtil.successResponse(refreshTokenResponse);
    }

    @PostMapping(ApiEndpoints.LOGOUT)
    public ResponseEntity<RestAPIResponse<Object>> logout() {
        Optional<String> currentUserEmail = SecurityUtil.getCurrentUserLogin();

        if (currentUserEmail.isEmpty()) {
            throw new AppException(ErrorCode.ACCOUNT_NOT_EXIST);
        }

        accountService.clearRefreshToken(currentUserEmail.get());

        ResponseCookie deleteCookie = ResponseCookie
                .from("refresh_token", null)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(0)
                .build();

        return responseUtil.successResponse(Optional.ofNullable(null),deleteCookie.toString());
    }
}