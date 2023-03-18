//package hanghae99.rescuepets.common.handler;
//
//import hanghae99.rescuepets.common.dto.CustomException;
//import hanghae99.rescuepets.common.dto.ErrorResponseDto;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.BindException;
//import org.springframework.web.bind.MissingRequestHeaderException;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//@Slf4j
//@RestControllerAdvice
//public class GrobalExceptionHandler {
////    @ExceptionHandler(value = {CustomException.class})
//    @ExceptionHandler(value = {Exception.class})
//    protected ResponseEntity<ErrorResponseDto> handleCustomException(CustomException e) {
//        log.error("handleCustomException throw CustomException : {}", e.getExceptionMessage());
//        return null;
////        return Response.toExceptionResponseEntity(e.getExceptionMessage());
//    }
//
//    //정규식
//    @ExceptionHandler({BindException.class})
//    public ResponseEntity<ErrorResponseDto> bindException(BindException ex) {
//        return null;
////        return Response.toAllExceptionResponseEntity(HttpStatus.BAD_REQUEST,
////                ex.getFieldError().getDefaultMessage(), null);
//    }
//
//    //마이리스트 토큰 없을시
//    @ExceptionHandler({MissingRequestHeaderException.class})
//    public ResponseEntity<ErrorResponseDto> missingRequestHeaderException(MissingRequestHeaderException ex) {
//        return null;
////        return Response.toAllExceptionResponseEntity(HttpStatus.BAD_REQUEST,
////                "로그인이 되어있지 않습니다.", null);
//    }
//
//    // 500
//    @ExceptionHandler({Exception.class})
//    public ResponseEntity<ErrorResponseDto> handleAll(final Exception ex) {
//        return null;
////        return Response.toAllExceptionResponseEntity(HttpStatus.BAD_REQUEST, ex.getMessage(), ex.toString());
//    }
//    @ExceptionHandler({Exception.class})
//    protected ResponseEntity<ErrorResponseDto> handleCustomRollBackException(CustomException e) {
////        log.error("handleCustomException throw CustomException : {}", e.getExceptionMessage());
//        return null;
////        return Response.toAllExceptionResponseEntity(e.getExceptionMessage(),e.getObject());
//    }
//
//}
