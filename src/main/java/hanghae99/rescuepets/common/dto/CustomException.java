package hanghae99.rescuepets.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CustomException extends RuntimeException {
    private ExceptionMessage exceptionMessage;
    private Object object;
}