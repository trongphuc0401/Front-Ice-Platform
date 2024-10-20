package vn.edu.likelion.front_ice.common.enums;

import lombok.Getter;

public interface BaseEnum<T> {
    T fromValue(String value);
}
