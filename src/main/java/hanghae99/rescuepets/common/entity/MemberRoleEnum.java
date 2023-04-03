package hanghae99.rescuepets.common.entity;

public enum MemberRoleEnum {
    ADMIN(Role.ADMIN),  // 관리자
    MANAGER(Role.MANAGER),  // 매니져(팻벤져스)
    MEMBER(Role.MEMBER),  // 일반회원
    BADMEMBER(Role.BADMEMBER);  // 불량회원

    private final String role;

    MemberRoleEnum(String role) {
        this.role = role;
    }

    public String getMemberRole() {
        return this.role;
    }

    public static class Role {
        public static final String ADMIN = "관리자";
        public static final String MEMBER = "일반회원";
        public static final String MANAGER = "매니져";
        public static final String BADMEMBER = "불량매니져";
    }
}
