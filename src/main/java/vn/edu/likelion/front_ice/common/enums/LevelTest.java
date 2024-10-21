package vn.edu.likelion.front_ice.common.enums;

public enum LevelTest {
    EASY("easy"),
    MEDIUM("medium"),
    HARD("hard");

    private final String level;

    LevelTest(String level) {
        this.level = level;
    }

    public String getLevel() {
        if (level == null) {
            return null;
        }
        return level;
    }
}
