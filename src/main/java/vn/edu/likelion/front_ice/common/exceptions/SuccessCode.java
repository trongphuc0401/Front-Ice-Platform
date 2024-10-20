package vn.edu.likelion.front_ice.common.exceptions;

import lombok.Getter;
import vn.edu.likelion.front_ice.common.api.RestAPIStatus;

/**
 * SuccessCode - Định nghĩa các trường hợp thành công trong file
 *
 * @param
 * @return
 * @throws
 */
@Getter
public enum SuccessCode {
    OK(RestAPIStatus.OK, 1200, "Success", "OK", "Thành công"),
    VERIFIED(RestAPIStatus.OK, 1201, "OTP verified", "OK", "OTP đã được xác thực"),
    SENT_OTP_EMAIL(RestAPIStatus.OK, 1202, "Sent OTP Email", "OK", "OTP đã được gửi về mail"),
    PASSWORD_CHANGED(RestAPIStatus.OK, 1203, "Password changed successful", "OK", "OTP cho quên mật khẩu đã được gửi về mail."),
    DOWNLOAD_SUCCESSFUL(RestAPIStatus.OK, 1204, "Download successful", "OK", "Tải file thành công."),

    // success challenge
    CHALLENGE_LIST_SUCCESS(RestAPIStatus.OK, 1205, "Challenge list retrieved successfully", "OK", "Lấy danh sách thử thách thành công"),
    ;

    private final int statusCode;
    private final int codeSuccess;
    private final String messageEng;
    private final String httpStatus;
    private final String messageVN;

    SuccessCode(RestAPIStatus restApiStatus, int codeSuccess, String messageEng, String httpStatus, String messageVN) {
        this.statusCode = restApiStatus.getCode();
        this.codeSuccess = codeSuccess;
        this.messageEng = messageEng;
        this.httpStatus = httpStatus;
        this.messageVN = messageVN;
    }
}
