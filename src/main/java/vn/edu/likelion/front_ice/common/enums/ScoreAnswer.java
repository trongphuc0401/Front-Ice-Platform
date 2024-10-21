package vn.edu.likelion.front_ice.common.enums;

public enum ScoreAnswer {
    NEW_BIE_EASY(25),
    NEW_BIE_MEDIUM(50),
    NEW_BIE_HARD(100),
    SILVER_EASY(38),
    SILVER_MEDIUM(75),
    SILVER_HARD(150),
    GOLD_EASY(50),
    GOLD_MEDIUM(100),
    GOLD_HARD(200);

    private final int score;

    ScoreAnswer(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

}

