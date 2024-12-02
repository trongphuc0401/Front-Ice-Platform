package vn.edu.likelion.front_ice.common.enums;

import lombok.Getter;

/**
 * Gender -
 *
 * @param
 * @return
 * @throws
 */
public enum Gender implements BaseEnum<Gender>{
    MALE(1,"male"),
    FEMALE(2,"female"),
    OTHER(3,"other");
    ;

    @Getter
    private final int code;
    private final String value;

    Gender(int gender, String value) {
        this.code = gender;
        this.value = value;
    }

    public static Gender fromGender(Integer code) {
        for (Gender gender : Gender.values()) {
            if (gender.code == code) {
                return gender;
            }
        }
        throw new IllegalArgumentException("Invalid Gender code: " + code);
    }

    @Override public Gender fromValue(String value) {
        return null;
    }
}
