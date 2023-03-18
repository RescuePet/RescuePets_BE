package hanghae99.rescuepets.common.entity;

public enum NeuterEnum {
    YES(Neuter.YES),  // 강아지
    NO(Neuter.NO),  // 고양이
    UNKNOWN(Neuter.UNKNOWN);  // 기타

    private final String neuter;

    NeuterEnum(String neuter) {
        this.neuter = neuter;
    }

    public String getNeuter() {
        return this.neuter;
    }

    public static class Neuter {
        public static final String YES = "NEUTER_YES";
        public static final String NO = "NEUTER_NO";
        public static final String UNKNOWN = "NEUTER_UNKNOWN";
    }
}
