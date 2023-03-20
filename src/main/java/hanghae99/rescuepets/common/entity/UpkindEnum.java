package hanghae99.rescuepets.common.entity;

public enum UpkindEnum {
    DOG(Upkind.DOG),  // 강아지
    CAT(Upkind.CAT),  // 고양이
    ETC(Upkind.ETC);  // 기타

    private final String upkind;

    UpkindEnum(String upkind) {
        this.upkind = upkind;
    }

    public String getUpkind() {
        return this.upkind;
    }

    public static class Upkind {
        public static final String DOG = "UPKIND_DOG";
        public static final String CAT = "UPKIND_CAT";
        public static final String ETC = "UPKIND_ETC";
    }
}
