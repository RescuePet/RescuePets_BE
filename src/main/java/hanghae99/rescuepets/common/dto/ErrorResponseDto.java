package hanghae99.rescuepets.common.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ErrorResponseDto {

    private Integer code;

    private String message;

    @Builder
    public ErrorResponseDto(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ErrorResponseDto of(Integer code, String message) {
        return ErrorResponseDto.builder().code(code).message(message).build();
    }
}