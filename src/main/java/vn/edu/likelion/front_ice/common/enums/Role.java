package vn.edu.likelion.front_ice.common.enums;

/**
 * Role -
 *
 * @param
 * @return
 * @throws
 */
public enum Role {

    ADMIN("admin"), //root
    CHALLENGE_MANAGER("challenge manager"),
    MENTOR("mentor"),
    ;

    private final String role;
    Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
