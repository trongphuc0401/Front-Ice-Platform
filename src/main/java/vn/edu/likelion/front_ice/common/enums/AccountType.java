package vn.edu.likelion.front_ice.common.enums;

public enum AccountType {

    CHALLENGE("challenge"),
    STAFF("staff"),
    RECRUITER("recruiter")
    ;


    private String accountType;

    AccountType(String staff) {
    }

    public String getAccountType() {
        return accountType;
    }
}
