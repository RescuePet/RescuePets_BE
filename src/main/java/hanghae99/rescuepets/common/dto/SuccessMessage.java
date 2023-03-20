package hanghae99.rescuepets.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SuccessMessage {

    SIGN_UP_SUCCESS(HttpStatus.CREATED, "회원가입이 완료 되었습니다."),
    LOGIN_SUCCESS(HttpStatus.OK,"로그인이 완료 되었습니다."),
    LOGOUT_SUCCESS(HttpStatus.OK,"로그아웃이 완료 되었습니다"),
    ACOUNT_CHECK_SUCCESS(HttpStatus.OK,"사용 가능한 아이디입니다."),
    EMAIL_CHECK_SUCCESS(HttpStatus.OK,"사용 가능한 이메일입니다."),
    USER_INFO_SUCCESS(HttpStatus.OK, "유저정보 불러오기 성공"),
    GOODS_DETAIL_SUCCESS(HttpStatus.OK, "상품 정보 불러오기 성공"),
    WITHDRAWAL_SUCCESS(HttpStatus.OK,"회원탈퇴에 성공하였습니다");
    private final HttpStatus httpStatus;
    private final String detail;
}
