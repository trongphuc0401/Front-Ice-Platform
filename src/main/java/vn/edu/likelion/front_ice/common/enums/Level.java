package vn.edu.likelion.front_ice.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Level -
 *
 * @param
 * @return
 * @throws
 */
public enum Level implements BaseEnum<Level> {

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

    @Override
    public Level fromValue(String value) {
        for (Level type : Level.values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid TypeChallenge value: " + value);
    }
}
