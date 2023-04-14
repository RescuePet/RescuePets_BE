package hanghae99.rescuepets.common.entity;

import com.fasterxml.jackson.annotation.JsonValue;
import hanghae99.rescuepets.common.dto.CustomException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static hanghae99.rescuepets.common.dto.ExceptionMessage.ENUM_NOT_FOUND;

@RequiredArgsConstructor
@Getter
public enum ReportTypeEnum {
    MEMBER_REPORT("회원 신고"),
    POST_REPORT("게시글 신고"),
    COMMENT_REPORT("댓글 신고");

    private final String value;

    public static ReportTypeEnum fromValue(String value) {
        for (ReportTypeEnum reportTypeEnum : ReportTypeEnum.values()) {
            if (reportTypeEnum.value.equalsIgnoreCase(value)) {
                return reportTypeEnum;
            }
        }
        throw new CustomException(ENUM_NOT_FOUND);
    }

    @JsonValue
    public String toValue() {
        return value;
    }

}
