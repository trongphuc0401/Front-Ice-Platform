package vn.edu.likelion.front_ice.common.exceptions;

import lombok.Getter;
import vn.edu.likelion.front_ice.common.api.RestAPIStatus;

/**
 * ErrorCode - Định nghĩa lõi trong file này để tránh các lỗi trùng lặp khi những thành viên trong nhóm cùng làm 1 service
 *
 * @param
 * @return
 * @throws
 */
@Getter
public enum ErrorCode {

    INVALID_KEY(RestAPIStatus.BAD_REQUEST,-100,"Invalid input object type!","Bad Request","Dữ liệu đầu vào không hợp lệ"),

    // error account
    USERNAME_TOO_SHORT(RestAPIStatus.BAD_REQUEST, -101, "Username must be at least {min} characters", "Bad Request", "Tên người dùng phải có ít nhất {min} ký tự"),
    PASSWORD_NO_NUMBER(RestAPIStatus.BAD_REQUEST, -102, "Password must contain at least one number", "Bad Request", "Mật khẩu phải chứa ít nhất một số"),
    EMAIL_INVALID(RestAPIStatus.BAD_REQUEST, -103, "Email is invalid", "Bad Request", "Email không hợp lệ"),
    ACCOUNT_NOT_EXIST(RestAPIStatus.BAD_REQUEST, -104, "User not exist", "Bad Request", "Người dùng không tồn tại"),
    ACCOUNT_EXIST(RestAPIStatus.EXISTED,-105,"Account exist in system","Bad Request","Người dùng đã tồn tại"),
    ACCOUNT_OR_PASSWORD_INCORRECT(RestAPIStatus.BAD_REQUEST,-106,"Account or password incorrect","Bad Request","Tên tài khoản hoặc mật khẩu không đúng"),
    PHOTO_UPLOAD_FAILED(RestAPIStatus.FAIL, -106, "Failed to save photo", "Fail", "Upload ảnh thất bại"),
    INVALID_DATE_FORMAT(RestAPIStatus.BAD_REQUEST, -107, "Invalid date format", "Bad Request", "Định dạng ngày không hợp lệ"),
    PARAM_INVALID(RestAPIStatus.BAD_REQUEST, -108, "{fieldName} is invalid", "Bad Request", "{fieldName} không hợp lệ"),
    PASSWORD_TOO_SHORT(RestAPIStatus.FAIL,-109,"Password must be between 8 and 20 characters","Fail","Mật khẩu phải từ 8 đến 20 ký tự"),
    PASSWORD_MISSING_UPPERCASE(RestAPIStatus.FAIL,-110,"Password must contain at least one uppercase letter","Fail","Mật khẩu phải chứa ít nhất một chữ cái in hoa"),
    PASSWORD_MISSING_LOWERCASE(RestAPIStatus.FAIL,111,"Password must be between 8 and 20 characters","Fail","Mật khẩu phải chứa ít nhất một chữ cái thường"),
    PASSWORD_MISSING_DIGIT(RestAPIStatus.FAIL,-112,"Password must contain at least one digit","Fail","Mật khẩu phải chứa ít nhất một chữ số"),
    PASSWORD_MISSING_SPECIAL(RestAPIStatus.FAIL,-113,"Password must contain at least one special character","Fail","Mật khẩu phải chứa ít nhất một ký tự đặc biệt"),
    PASSWORD_CONTAINS_WHITESPACE(RestAPIStatus.FAIL,-114,"Password must not contain whitespace","Fail","Mật khẩu không được chứa khoảng trắng"),
    INVALID_IMAGE_FORMAT(RestAPIStatus.BAD_REQUEST,-115,"Image format not supported. Only JPEG,JPG,PNG is allowed", "Bad Request", "Định dạng ảnh không được hỗ trợ. Chỉ JPEG,JPG,PNG được phép"),
    CONFIRM_PASSWORD_NOT_MATCH(RestAPIStatus.BAD_REQUEST, -116, "Confirm Password not match", "Bad Request", "Mật khẩu xác thực không trùng khớp"),
    EMAIL_SENDING_FAILED(RestAPIStatus.BAD_REQUEST, -117, "Failed to send email", "Bad Request", "Gửi email thất bại"),
    ACCOUNT_UNAUTHENTICATED(RestAPIStatus.BAD_REQUEST, -118, "Account unauthenticated", "Bad Request", "Tài khoản chưa được xác thực OTP"),
    OTP_INVALID(RestAPIStatus.BAD_REQUEST, -119, "OTP invalid", "Bad Request", "OTP không hợp lệ"),
    PASSWORD_RESET_FAILED(RestAPIStatus.FAIL, -120, "Reset Password Fail", "Fail", "Đặt lại mật khẩu thất bại"),
    RESET_TOKEN_EXPIRED(RestAPIStatus.BAD_REQUEST, -121, "Reset token expired", "Bad Request", "Mã đặt lại mật khẩu đã hết hạn"),
    RESET_TOKEN_INVALID(RestAPIStatus.BAD_REQUEST, -122, "Invalid reset token", "Bad Request", "Mã đặt lại mật khẩu không hợp lệ"),
    INVALID_REFRESH_TOKEN(RestAPIStatus.UNAUTHORIZED, -123, "Refresh token is invalid or expired", "Unauthorized", "Token làm mới không hợp lệ hoặc hết hạn"),
    CV_UPLOAD_FAILED(RestAPIStatus.FAIL, -124, "Failed to upload CV", "Fail", "Upload CV thất bại"),
    INVALID_JWT_TOKEN(RestAPIStatus.BAD_REQUEST,-125,"Invalid Jwt Token","Bad Request","JWT Token không hợp lệ"),
    // error challenger
    CHALLENGER_NOT_EXIST(RestAPIStatus.BAD_REQUEST,-201,"Challenger not exist","Bad Request","Challenger không tồn tại"),
    CHALLENGER_HAS_FOLLOWED_RECRUITER(RestAPIStatus.BAD_REQUEST,-202,"Challenger has followed this Recruiter","Bad Request","Challenger đã theo dõi Recruiter này rồi"),

    // error challenge
    CHALLENGE_NOT_EXIST(RestAPIStatus.BAD_REQUEST,-301 ,"Challenge not exist","Bad Request","Challenge không tồn tại"),
    ASSETS_UPLOAD_FAILED(RestAPIStatus.FAIL,-302 ,"Failed to upload Assets","Fail","Upload Assets thất bại"),
    CATEGORY_NOT_EXIST(RestAPIStatus.BAD_REQUEST,-303 ,"Category not exist","Bad Request","Category không tồn tại"),
    IMAGE_DESKTOP(RestAPIStatus.BAD_REQUEST,-304 ,"Image desktop not exist","Bad Request","Hình ảnh desktop không tồn tại"),
    IMAGE_MOBILE(RestAPIStatus.BAD_REQUEST,-305 ,"Image mobile not exist","Bad Request","Hình ảnh mobile không tồn tại"),
    IMAGE_TABLET(RestAPIStatus.BAD_REQUEST,-306 ,"Image tablet not exist","Bad Request","Hình ảnh mobile không tồn tại"),


    NOT_FOUND_CHALLENGE_SAMPLE(RestAPIStatus.FAIL,-305 ,"Not found challenge sample","Bad Request","Không tìm thấy challenge mẫu"),

    // error solution
    SOLUTION_NOT_EXIST(RestAPIStatus.BAD_REQUEST, -401,"Solution not exist","Bad Request","Solution không tồn tại"),
    YOU_HAVE_ALREADY_JOINED(RestAPIStatus.BAD_REQUEST, -402,"You have already joined","Bad Request","Bạn đã tham gia thử thách này rồi"),

    // error level
    LEVEL_NOT_EXIST(RestAPIStatus.BAD_REQUEST, -501,"Level not exist","Bad Request","Level không tồn tại"),

    // error recruiter
    RECRUITER_NOT_EXIST(RestAPIStatus.BAD_REQUEST, -601,"Recruiter not exist","Bad Request","Recruiter không tồn tại"),

    // error mentor
    MENTOR_NOT_EXIST(RestAPIStatus.BAD_REQUEST, -701,"Mentor not exist","Bad Request","Mentor không tồn tại"),

    // error manager
    MANAGER_NOT_EXIST(RestAPIStatus.BAD_REQUEST,-801,"Manager not exist","Bad Request","Manager không tồn tại"),

    DELETE_FAILED(RestAPIStatus.CAN_NOT_DELETE,-9996,"Delete failed","Not found","Xoá thất bại"),

    UPDATE_FAILED(RestAPIStatus.BAD_REQUEST,-9997,"Update failed","Not found","Cập nhật thất bại"),
    QUERY_NOT_FOUND(RestAPIStatus.BAD_REQUEST,-9998,"Query not found","Not found","Truy vấn không thành công"),
    UNCATEGORIZED_EXCEPTION(RestAPIStatus.BAD_REQUEST,-9999,"Uncategorized error","Bad request","Lỗi chưa được định nghĩa");

    private final int statusCode;
    private final int codeError;
    private final String messageEng;
    private final String httpStatus;
    private final String messageVN;



    ErrorCode(RestAPIStatus restApiStatus ,int errorCode, String messageEng, String httpStatus, String messageVN) {
        this.statusCode = restApiStatus.getCode();
        this.codeError = errorCode;
        this.messageEng = messageEng;
        this.httpStatus = httpStatus;
        this.messageVN = messageVN;
    }

}
