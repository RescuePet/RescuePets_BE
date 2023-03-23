package hanghae99.rescuepets.common.entity;

public enum PostTypeEnum {
    CATCH(PostType.CATCH),  // 의심발견신고
    MISSING(PostType.MISSING);  // 실종발생신고

    private final String postType;

    PostTypeEnum(String postType) {
        this.postType = postType;
    }

    public String getPostType() {
        return this.postType;
    }

    public static class PostType {
        public static final String CATCH = "POSTTYPE_CATCH";
        public static final String MISSING = "POSTTYPE_MISSING";
    }
}
