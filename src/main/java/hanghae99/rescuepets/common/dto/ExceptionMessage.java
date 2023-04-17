package hanghae99.rescuepets.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ExceptionMessage {
    /* 400 BAD_REQUEST : 잘못된 요청 */
    TEST(BAD_REQUEST, "TEST Exception"),
    ID_PASSWORDS_INCORRECT(BAD_REQUEST, "아이디,비밀번호를 확인해주세요"),
    MISMATCH_REFRESH_TOKEN(BAD_REQUEST, "리프레시 토큰의 유저 정보가 일치하지 않습니다."),
    INVALID_TOKEN(BAD_REQUEST, "Invalid JWT signature, 유효하지 않는 JWT 서명 입니다."),
    ILLEGAL_TOKEN(BAD_REQUEST, "JWT claims is empty, 잘못된 JWT 토큰 입니다."),
    CREATE_CHAT_ROOM_EXCEPTION(BAD_REQUEST, "자신에게 채팅할 수 없습니다."),
    NICKNAME_WITH_SPACES(BAD_REQUEST,"공백이 포함된 닉네임입니다."),
    KAKAO_UNLINK_FAIL(BAD_REQUEST, "회원탈퇴 실패"),
    MAIL_SEND_FAIL(BAD_REQUEST, "메일전송 실패"),
    CONNECTION_ERROR(BAD_REQUEST, "연결 실패"),
    INVALID_SEARCH_KEY(BAD_REQUEST, "검색키가 올바르지 않습니다."),


    /* 401 UNAUTHORIZED : 인증되지 않은 사용자 */
    UNAUTHORIZED_MEMBER(UNAUTHORIZED, "해당 회원이 존재하지 않습니다."),
    UNAUTHORIZED_ADMIN(UNAUTHORIZED, "관리자가 아닙니다."),
    UNAUTHORIZED_UPDATE_OR_DELETE(UNAUTHORIZED,"작성자만 수정/삭제할 수 있습니다."),
    UNAUTHORIZED_SAVE(UNAUTHORIZED,"작성자만 저장할 수 있습니다."),
    UNAUTHORIZED_MANAGER(UNAUTHORIZED, "매니저 등급이 아닙니다."),


    /* 403 FORBIDDEN : 권한 없음 */
    BAD_MEMBER_FORBIDDEN(FORBIDDEN, "현재 활동 정지된 계정입니다."),
    PROFANITY_CHECK(FORBIDDEN, "닉네임에는 비속어를 포함할 수 없습니다."),
    NOT_ALLOWED_GRADE(FORBIDDEN, "해당 등급설정에 대한 권한이 없습니다."),
    TOO_FREQUENT_POST(FORBIDDEN, "한 번에 너무 많은 게시글들을 작성할 수 없습니다. 잠시 후 다시 시도해주세요."),
    TOO_FREQUENT_COMMENT(FORBIDDEN, "한 번에 너무 많은 댓글들을 작성할 수 없습니다. 잠시 후 다시 시도해주세요."),
    TOO_FREQUENT_CHAT(FORBIDDEN, "한 번에 너무 많은 채팅을 작성할 수 없습니다. 잠시 후 다시 시도해주세요."),


    /* 404 NOT_FOUND : Resource 를 찾을 수 없음 */
    POST_TYPE_INCORRECT(NOT_FOUND, "게시글 타입이 적절하지 않습니다."),
    POST_NOT_FOUND(NOT_FOUND, "대상 게시글을 찾을 수 없습니다."),
    ENUM_NOT_FOUND(NOT_FOUND, "해당 이넘값은 존재하지 않습니다."),
    NOT_FOUND_IMAGE(NOT_FOUND, "이미지가 없습니다."),
    POST_ALREADY_DELETED(NOT_FOUND, "삭제된 게시글입니다."),
    COMMENT_NOT_FOUND(NOT_FOUND, "대상 댓글을 찾을 수 없습니다."),
    MEMBER_NOT_FOUND(NOT_FOUND, "회원을 찾을 수 없습니다."),
    CHATROOM_NOT_FOUND(NOT_FOUND, "채팅방이 존재하지 않습니다."),
    NOT_FOUND_PET_INFO(NOT_FOUND,"해당 유기동물 정보가 없습니다."),
    NOT_FOUND_PET_INFO_SCRAP_MEMBER(NOT_FOUND,"요청하신 회원은 해당 유기 동물이 스크랩되어있지 않습니다."),
    NOT_FOUND_SCRAP(NOT_FOUND,"스크랩되어있지 않습니다."),
    NOT_FOUND_REPORT(NOT_FOUND,"신고가 되어있지 않습니다."),
    /* 409 CONFLICT : Resource 의 현재 상태와 충돌. 보통 중복된 데이터 존재 */
    DUPLICATE_RESOURCE_PET_INFO_SCRAP(CONFLICT, "관심 유기동물에 이미 존재합니다"),
    DUPLICATE_RESOURCE_PET_INFO_INQUIRY(CONFLICT, "해당 유기동물에 문의한 내역이 존재합니다"),
    DUPLICATE_NICKNAME(CONFLICT,"중복된 닉네임이 존재합니다."),
    DUPLICATE_EMAIL(CONFLICT,"중복된 이메일이 존재합니다."),
    SELF_REFERENCE_NOT_ALLOWED(CONFLICT,"자기 자신을 대상으로 지정할 수 없습니다."),
    SAME_ROLE_NOT_ALLOWED(CONFLICT,"선택한 유저는 이미 해당 등급입니다."),
    ALREADY_SCRAP(CONFLICT, "이미 스크랩되었습니다."),
    SAME_POST_NOT_ALLOWED(CONFLICT, "링크 시작점과 같은 게시물을 대상으로 지정할 수 없습니다."),
    ALREADY_LINKED(CONFLICT, "이미 존재하는 링크입니다."),
    ALREADY_REPORT(CONFLICT, "이미 신고되었습니다.");

    private final HttpStatus httpStatus;
    private final String detail;
}
