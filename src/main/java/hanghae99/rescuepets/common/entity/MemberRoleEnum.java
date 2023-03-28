package hanghae99.rescuepets.common.entity;

public enum MemberRoleEnum {
    MEMBER(Role.MEMBER),  // 일반회원
    MANAGER(Role.MANAGER),  // 매니져
    ADMIN(Role.ADMIN);  // 관리자

    private final String role;

    MemberRoleEnum(String role) {
        this.role = role;
    }

    public String getMemberRole() {
        return this.role;
    }

    public static class Role {
        public static final String MEMBER = "ROLE_MEMBER";
        public static final String MANAGER = "ROLE_MANAGER";
        public static final String ADMIN = "ROLE_ADMIN";
    }
}
