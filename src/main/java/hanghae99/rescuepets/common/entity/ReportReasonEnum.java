package hanghae99.rescuepets.common.entity;

import com.fasterxml.jackson.annotation.JsonValue;
import hanghae99.rescuepets.common.dto.CustomException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static hanghae99.rescuepets.common.dto.ExceptionMessage.ENUM_NOT_FOUND;

@RequiredArgsConstructor
@Getter
public enum ReportReasonEnum {
    VIOLENCE("폭력적 표현"),
    INCORRECT_INFORMATION("부정확한 정보"),
    INDECENT_EXPRESSION("선정적 표현"),
    ETC("기타");

    private final String value;

    public static ReportReasonEnum fromValue(String value) {
        for (ReportReasonEnum reportReasonEnum : ReportReasonEnum.values()) {
            if (reportReasonEnum.value.equalsIgnoreCase(value)) {
                return reportReasonEnum;
            }
        }
        throw new CustomException(ENUM_NOT_FOUND);
    }

    @JsonValue
    public String toValue() {
        return value;
    }

}

