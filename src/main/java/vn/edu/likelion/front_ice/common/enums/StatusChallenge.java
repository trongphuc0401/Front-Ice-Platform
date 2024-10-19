package vn.edu.likelion.front_ice.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * StatusChallenge -
 *
 * @param
 * @return
 * @throws
 */
public enum StatusChallenge {

    PROCESSING("processing"),
    APPROVED("approved"),
    HIDE("hide"),
    CANCEL("cancel");

    private final String value;

    StatusChallenge(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
