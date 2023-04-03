package hanghae99.rescuepets.common.entity;

public enum PetStateEnum {
    NOTICE("공고중"),
    PROTECT("보호중"),
    END("종료");

    private final String korean;

    PetStateEnum(String korean) {
        this.korean = korean;
    }

    public String getKorean() {
        return korean;
    }

//    public static PetStateEnum values(String value) {
//        for (PetStateEnum petStateEnum : PetStateEnum.values()) {
//            if (petStateEnum.korea.equalsIgnoreCase(value)) {
//                return petStateEnum;
//            }
//        }
//        throw new IllegalArgumentException("Invalid house type value: " + value);
//    }
}
