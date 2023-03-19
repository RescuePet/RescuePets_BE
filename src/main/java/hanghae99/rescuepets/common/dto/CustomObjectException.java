package hanghae99.rescuepets.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CustomObjectException extends RuntimeException {
    private final ExceptionMessage exceptionMessage;
    private final Object object;
}
