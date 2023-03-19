package hanghae99.rescuepets.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
public class CustomException extends RuntimeException {
    private ExceptionMessage exceptionMessage;
}