package vn.edu.likelion.front_ice.common.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * DateTimeUtil -
 *
 * @param
 * @return
 * @throws
 */
public class DateTimeUtil {
    /**
     * Chuyển đổi số giây (s) thành LocalDateTime.
     * @param seconds Số giây kể từ thời điểm Unix Epoch.
     * @return LocalDateTime tương ứng.
     */
    public static LocalDateTime convertSecondsToLocalDateTime(Long seconds) {
        Instant instant = Instant.ofEpochSecond(seconds);  // Chuyển đổi giây thành Instant
        return LocalDateTime.ofInstant(instant, ZoneOffset.UTC);  // Chuyển đổi Instant thành LocalDateTime ở múi giờ UTC
    }
}
