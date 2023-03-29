package hanghae99.rescuepets.common.handler;

import hanghae99.rescuepets.common.dto.CustomException;
import hanghae99.rescuepets.common.dto.CustomObjectException;
import hanghae99.rescuepets.common.dto.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    //CustomException
    @ExceptionHandler(value = {CustomException.class})
    protected ResponseEntity<ResponseDto> handleCustomException(CustomException e) {
        log.error("handleCustomException throw CustomException : {}", e.getExceptionMessage());
        return ResponseDto.toExceptionResponseEntity(e.getExceptionMessage());
    }

    //정규식
    @ExceptionHandler({BindException.class})
    public ResponseEntity<ResponseDto> bindException(BindException ex) {
        return ResponseDto.toAllExceptionResponseEntity(HttpStatus.BAD_REQUEST,
                ex.getFieldError().getDefaultMessage(), null);
    }

    //마이리스트 토큰 없을시
    @ExceptionHandler({MissingRequestHeaderException.class})
    public ResponseEntity<ResponseDto> missingRequestHeaderException(MissingRequestHeaderException ex) {
        return ResponseDto.toAllExceptionResponseEntity(HttpStatus.BAD_REQUEST,
                "로그인이 되어있지 않습니다.", null);
    }

    // 500
    @ExceptionHandler({Exception.class})
    public ResponseEntity<ResponseDto> handleAll(final Exception ex) {
        return ResponseDto.toAllExceptionResponseEntity(HttpStatus.BAD_REQUEST, ex.getMessage(), ex.toString());
    }

//    CustomException에서 object를 사용할 경우
    @ExceptionHandler({CustomObjectException.class})
    protected ResponseEntity<ResponseDto> handleCustomRollBackException(CustomObjectException e) {
        log.error("handleCustomException throw CustomException Message: {}", e.getExceptionMessage());
        log.error("handleCustomException throw CustomException Object: {}", e.getObject());
        return ResponseDto.toAllExceptionResponseEntity(e.getExceptionMessage(), e.getObject());
    }
}
