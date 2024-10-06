package vn.edu.likelion.front_ice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.likelion.front_ice.common.api.ResponseUtil;
import vn.edu.likelion.front_ice.common.api.RestAPIResponse;
import vn.edu.likelion.front_ice.common.constants.ApiEndpoints;
import vn.edu.likelion.front_ice.common.exceptions.AppException;
import vn.edu.likelion.front_ice.common.exceptions.ErrorCode;
import vn.edu.likelion.front_ice.dto.request.*;
import vn.edu.likelion.front_ice.dto.response.RegisterResponse;
import vn.edu.likelion.front_ice.entity.AccountEntity;
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

        return responseUtil.successResponse(accountService.login(loginRequest), resCookies.toString());
    }

    @PostMapping(ApiEndpoints.SEND_OTP)
    public ResponseEntity<RestAPIResponse<Object>> sendOTP(@RequestParam String gmail) {
        return responseUtil.successResponse(accountService.generateOTP(gmail));
    }

    @PostMapping(ApiEndpoints.VERIFY_EMAIL)
    public ResponseEntity<RestAPIResponse<Object>> OTP(@RequestBody VerifyEmailRequest verifyEmailRequest) {
        boolean flag = accountService.verifyOTP(verifyEmailRequest.getEmail(), verifyEmailRequest.getOtp());

        if (flag) {
            accountService.clearOTP(verifyEmailRequest.getEmail());
            return responseUtil.successResponse("OTP verified");
        } else {
            return responseUtil.successResponse("OTP not verified");
        }
    }

    @PostMapping(ApiEndpoints.FORGOT_PASSWORD)
    public ResponseEntity<RestAPIResponse<Object>> forgotPassword(@RequestBody ForgotPasswordRequest forgotPasswordRequest) {
        Optional<AccountEntity> accountOptional = accountService.findByEmail(forgotPasswordRequest.getEmail());
        if (accountOptional.isEmpty()) {
            throw new AppException(ErrorCode.ACCOUNT_NOT_EXIST);
        }

        String otpMessage = accountService.generateForgotPasswordOTP(forgotPasswordRequest.getEmail());
        return responseUtil.successResponse(otpMessage);
    }

    @PostMapping(ApiEndpoints.VERIFY_FORGOT_PASSWORD_OTP)
    public ResponseEntity<RestAPIResponse<Object>> verifyForgotPasswordOTP(@RequestBody VerifyForgotPasswordOTPRequest verifyOtpRequest) {
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
            return responseUtil.successResponse("Mật khẩu đã được đặt lại thành công.");
        } else {
            throw new AppException(ErrorCode.PASSWORD_RESET_FAILED);
        }
    }
}
