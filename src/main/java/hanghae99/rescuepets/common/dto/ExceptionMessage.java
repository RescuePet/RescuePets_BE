package hanghae99.rescuepets.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ExceptionMessage {
    /* 400 BAD_REQUEST : 잘못된 요청 */
    MISMATCH_REFRESH_TOKEN(BAD_REQUEST, "리프레시 토큰의 유저 정보가 일치하지 않습니다"),
    INVALID_TOKEN(BAD_REQUEST, "Invalid JWT signature, 유효하지 않는 JWT 서명 입니다"),
    ILLEAGAL_TOKEN(BAD_REQUEST, "JWT claims is empty, 잘못된 JWT 토큰 입니다."),

    NICKNAME_WITH_SPACES(BAD_REQUEST,"공백이 포함된 닉네임입니다."),
    IMAGE_INVALID(BAD_REQUEST,"이미지가 잘못 되었습니다."),


    /* 401 UNAUTHORIZED : 인증되지 않은 사용자 */
    UNAUTHORIZED_MEMBER(UNAUTHORIZED, "현재 내 계정 정보가 존재하지 않습니다"),
    UNAUTHORIZED_ADMIN(UNAUTHORIZED, "관리자가 아닙니다."),
    UNAUTHORIZED_UPDATE_OR_DELETE(UNAUTHORIZED,"작성자만 수정/삭제할 수 있습니다."),

    /* 403 FORBIDDEN : 권한 없음 */
    USER_FORBIDDEN(FORBIDDEN, "권한이 없습니다."),
    CART_GOODS_DELETE_FORBIDDEN(FORBIDDEN,"해당 장바구니의 삭제 권한이 없습니다."),

    /* 404 NOT_FOUND : Resource 를 찾을 수 없음 */
    MEMBER_NOT_FOUND(NOT_FOUND, "아이디,비밀번호를 확인해주세요"),
    GOODS_NOT_FOUND(NOT_FOUND, "해당 상품을 찾을 수 없습니다"),
    REFRESH_TOKEN_NOT_FOUND(NOT_FOUND, "로그아웃 된 사용자입니다"),

    /* 409 CONFLICT : Resource 의 현재 상태와 충돌. 보통 중복된 데이터 존재 */
    DUPLICATE_RESOURCE(CONFLICT, "데이터가 이미 존재합니다"),
    DUPLICATE_USER(CONFLICT,"중복된 사용자가 존재합니다."),
    DUPLICATE_GOODS(CONFLICT,"해당 상품의 이름은 이미 존재합니다."),
    DUPLICATE_NICKNAME(CONFLICT,"중복된 닉네임이 존재합니다."),
    DUPLICATE_EMAIL(CONFLICT,"중복된 이메일이 존재합니다.");


    private final HttpStatus httpStatus;
    private final String detail;
}