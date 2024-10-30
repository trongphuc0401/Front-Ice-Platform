package vn.edu.likelion.front_ice.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * Level -
 *
 * @param
 * @return
 * @throws
 */
public enum Level implements BaseEnum<Level> {

    NEWBIE(1, "Newbie"),
    BRONZE(2, "Bronze"),
    SILVER(3, "Silver"),
    GOLD(4, "Gold"),
    PLATINUM(5, "Platinum"),
    DIAMOND(6, "Diamond");

    ;
    @Getter
    private final int rank;
    private final String value;

    private Level(int rank,String value) {
        this.rank = rank;
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

    public static Level fromRank(int rank) {
        for (Level level : Level.values()) {
            if (level.rank == rank) {
                return level;
            }
        }
        throw new IllegalArgumentException("Invalid Level rank: " + rank);
    }

    public Level getNextLevel() {
        return fromRank(this.rank + 1);
    }
}
