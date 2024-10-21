package vn.edu.likelion.front_ice.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * TypeChallenge -
 *
 * @param
 * @return
 * @throws
 */
public enum TypeChallenge implements BaseEnum<TypeChallenge> {
    FREE("free"),
    FREE_PLUS_PLUS("free++"),
    PREMIUM("premium");

    private final String value;

    TypeChallenge(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @Override
    public TypeChallenge fromValue(String value) {
        for (TypeChallenge type : TypeChallenge.values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid TypeChallenge value: " + value);
    }
}
