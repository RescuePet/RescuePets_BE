package hanghae99.rescuepets.common.entity;

public enum NeuterEnum {
    YES(Neuter.YES),  // 중성화 함
    NO(Neuter.NO),  // 중성화 안 함
    UNKNOWN(Neuter.UNKNOWN);  // 알 수 없음

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
