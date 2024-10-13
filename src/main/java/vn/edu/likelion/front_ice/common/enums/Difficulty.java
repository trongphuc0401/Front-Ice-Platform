package vn.edu.likelion.front_ice.common.enums;

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
    public String getValue() {
        return value;
    }
}
