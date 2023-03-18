package hanghae99.rescuepets.common.entity;

public enum SexEnum {
    MALE(Sex.MALE),  // 강아지
    FEMALE(Sex.FEMALE),  // 고양이
    UNKNOWN(Sex.UNKNOWN);  // 기타

    private final String sex;

    SexEnum(String sex) {
        this.sex = sex;
    }

    public String getSex() {
        return this.sex;
    }

    public static class Sex {
        public static final String MALE = "SEX_MALE";
        public static final String FEMALE = "SEX_FEMALE";
        public static final String UNKNOWN = "SEX_UNKNOWN";
    }
}
