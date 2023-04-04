package hanghae99.rescuepets.common.entity;

public enum PostTypeEnum {
    CATCH("catch"),  // 의심발견신고
    MISSING("missing");  // 실종발생신고
    private final String postType;
    PostTypeEnum(String postType) {
        this.postType = postType;
    }
    public String getPostType() {
        return this.postType;
    }
}
