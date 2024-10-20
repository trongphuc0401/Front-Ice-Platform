package vn.edu.likelion.front_ice.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Status -
 *
 * @param
 * @return
 * @throws
 */
public enum StatusSolution {

    PROCESSING("processing"),
    APPROVED("approved"),
    CANCEL("cancel");

    private final String value;

     StatusSolution(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
