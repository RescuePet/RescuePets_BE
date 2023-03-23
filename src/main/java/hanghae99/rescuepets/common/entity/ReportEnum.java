package hanghae99.rescuepets.common.entity;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ReportEnum {
    INAPPOITE_WORD("부적절한단어"),
    FALSE_INFORMATION("거짓정보"),
    BODY_EXPOSURE("신체노출"),
    ETC("기타");

    private final String value;


    public static ReportEnum fromValue(String value) {
        for (ReportEnum reportEnum : ReportEnum.values()) {
            if (reportEnum.value.equalsIgnoreCase(value)) {
                return reportEnum;
            }
        }
        throw new IllegalArgumentException("Invalid house type value: " + value);
    }

    @JsonValue
    public String toValue() {
        return value;
    }

}

