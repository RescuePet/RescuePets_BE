package hanghae99.rescuepets.common.entity;

public enum SexEnum {
    MALE(Sex.MALE),  // 수컷
    FEMALE(Sex.FEMALE),  // 암컷
    UNKNOWN(Sex.UNKNOWN);  // 미상

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
