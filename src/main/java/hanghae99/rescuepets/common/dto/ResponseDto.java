package hanghae99.rescuepets.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseDto<T> {

    private boolean success;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T result;

    public static <T> ResponseDto<T> success(T result) {
        return new ResponseDto<>(true, result);
    }

    public static <T> ResponseDto<T> fail() {
        return new ResponseDto<>(false, null);
    }

    @Getter
    @AllArgsConstructor
    public static class Error {
        private String errorMessage;
    }
}
