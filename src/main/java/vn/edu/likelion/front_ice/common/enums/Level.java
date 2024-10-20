package vn.edu.likelion.front_ice.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Level -
 *
 * @param
 * @return
 * @throws
 */
public enum Level {

    NEWBIE("newbie"),
    BRONZE("bronze"),
    SILVER("silver"),
    GOLD("gold"),
    PLATINUM("platinum"),
    DIAMOND("diamond"),

    ;
    private final String value;

    private Level(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
