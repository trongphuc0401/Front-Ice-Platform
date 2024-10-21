package vn.edu.likelion.front_ice.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * ChallengeAccessStatus -
 *
 * @param
 * @return
 * @throws
 */
public enum ChallengeAccessStatus {
    VISITED("visited"),
    JOINED("joined"),
    SUBMITTED("submitted"),
    REPORTED("reported"),
    ERROR_LEVEL("error_level"),
    ERROR_TYPE("error_type")
    ;

    private final String value;

    private ChallengeAccessStatus(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
