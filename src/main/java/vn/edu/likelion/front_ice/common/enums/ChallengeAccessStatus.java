package vn.edu.likelion.front_ice.common.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * ChallengeAccessStatus -
 *
 * @param
 * @return
 * @throws
 */
@Getter
@AllArgsConstructor
public enum ChallengeAccessStatus {
    PUBLIC_ACCESS("PUBLIC_ACCESS", "Truy cập công khai"),
    PREMIUM_REQUIRED("PREMIUM_REQUIRED", "Vui lòng nâng cấp tài khoản premium để truy cập thử thách này."),
    LEVEL_RESTRICTED("LEVEL_RESTRICTED", "Cấp độ của bạn không đủ để truy cập thử thách này."),
    NOT_JOINED("NOT_JOINED", "Bạn chưa tham gia thử thách này."),
    JOINED("JOINED", "Bạn đã tham gia thử thách này."),
    SUBMITTED("SUBMITTED", "Bạn đã nộp bài làm cho thử thách này."),
    REPORTED("REPORTED", "Giải pháp của bạn đã bị báo cáo.");

    private final String status;
    private final String message;
}
