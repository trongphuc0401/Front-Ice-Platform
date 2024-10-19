package vn.edu.likelion.front_ice.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Difficulty -
 *
 * @param
 * @return
 * @throws
 */
public enum Difficulty {

    EASY("easy"),
    MEDIUM("medium"),
    HARD("hard"),
    ;
    private final String value;
    private Difficulty(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
