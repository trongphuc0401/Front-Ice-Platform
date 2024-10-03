package vn.edu.likelion.front_ice.common.enums;

public enum AccountType {

    CHALLENGE("challenge"),
    STAFF("staff"),
    RECRUITER("recruiter")
    ;


    private final String accounType;

    AccountType(String accounType) {
        this.accounType = accounType;
    }

    public String getAccountType() {
        return accounType;
    }
}
