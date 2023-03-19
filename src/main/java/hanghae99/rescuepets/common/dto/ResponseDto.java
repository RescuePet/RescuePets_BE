package hanghae99.rescuepets.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@Builder
@AllArgsConstructor
public class ResponseDto<T> {
    private boolean status;
    private String message;
    private T data;

    public static ResponseEntity<ResponseDto> toExceptionResponseEntity(ExceptionMessage exceptionMessage) {
        return ResponseEntity
                .status(exceptionMessage.getHttpStatus())
                .body(ResponseDto.builder()
                        .status(!exceptionMessage.getHttpStatus().isError())
                        .message(exceptionMessage.getDetail())
                        .data(exceptionMessage)
                        .build()
                );
    }

    public static ResponseEntity<ResponseDto> toAllExceptionResponseEntity(HttpStatus httpStatus, String message, Object data) {
        return ResponseEntity
                .status(httpStatus)
                .body(ResponseDto.builder()
                        .status(false)
                        .message(message)
                        .data(data)
                        .build()
                );
    }
    public static ResponseEntity<ResponseDto> toAllExceptionResponseEntity(ExceptionMessage exceptionMessage , Object data) {
        return ResponseEntity
                .status(exceptionMessage.getHttpStatus())
                .body(ResponseDto.builder()
                        .status(!exceptionMessage.getHttpStatus().isError())
                        .message(exceptionMessage.getDetail())
                        .data(data)
                        .build()
                );
    }

    public static ResponseEntity<ResponseDto> toResponseEntity(SuccessMessage message) {
        return ResponseEntity
                .status(message.getHttpStatus())
                .body(ResponseDto.builder()
                        .status(!message.getHttpStatus().isError())
                        .message(message.getDetail())
                        .data(message)
                        .build()
                );
    }
    public static<T> ResponseEntity<ResponseDto> toResponseEntity(SuccessMessage message, T data) {
        return ResponseEntity
                .status(message.getHttpStatus())
                .body(ResponseDto.builder()
                        .status(!message.getHttpStatus().isError())
                        .message(message.getDetail())
                        .data(data)
                        .build()
                );
    }
}