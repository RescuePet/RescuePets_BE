package hanghae99.rescuepets.common.entity;

public enum MemberRoleEnum {
    ADMIN(Role.ADMIN),  // 관리자
    MANAGER(Role.MANAGER),  // 매니저(팻벤져스)
    MEMBER(Role.MEMBER),  // 일반회원
    BAD_MEMBER(Role.BAD_MEMBER);  // 불량회원

    private final String role;

    MemberRoleEnum(String role) {
        this.role = role;
    }

    public String getMemberRole() {
        return this.role;
    }

    public static class Role {
        public static final String ADMIN = "ROLE_ADMIN";
        public static final String MANAGER = "ROLE_MANAGER";
        public static final String MEMBER = "ROLE_MEMBER";
        public static final String BAD_MEMBER = "ROLE_BAD_MEMBER";
    }
}