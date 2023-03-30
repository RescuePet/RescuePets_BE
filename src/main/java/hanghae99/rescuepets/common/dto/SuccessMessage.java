package hanghae99.rescuepets.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SuccessMessage {
    TEST_SUCCESS(HttpStatus.CREATED, "회원가입이 완료 되었습니다."),
    SIGN_UP_SUCCESS(HttpStatus.CREATED, "회원가입이 완료 되었습니다."),

    POST_WRITING_SUCCESS(HttpStatus.CREATED, "게시물 작성이 완료 되었습니다."),
    COMMENT_WRITING_SUCCESS(HttpStatus.CREATED, "댓글 작성이 완료 되었습니다."),
    POST_LINKING_SUCCESS(HttpStatus.CREATED, "링크 생성이 완료 되었습니다."),
    POST_MODIFYING_SUCCESS(HttpStatus.CREATED, "게시글 수정이 완료 되었습니다."),
    MEMBER_EDIT_SUCCESS(HttpStatus.CREATED, "회원정보 수정이 완료되었습니다."),
    COMMENT_MODIFYING_SUCCESS(HttpStatus.CREATED, "댓글 수정이 완료 되었습니다."),
    LOGIN_SUCCESS(HttpStatus.OK,"로그인이 완료 되었습니다."),
    LOGOUT_SUCCESS(HttpStatus.OK,"로그아웃이 완료 되었습니다"),
    ACCOUNT_CHECK_SUCCESS(HttpStatus.OK,"사용 가능한 아이디입니다."),
    EMAIL_CHECK_SUCCESS(HttpStatus.OK,"사용 가능한 이메일입니다."),
    POST_READING_SUCCESS(HttpStatus.OK,"게시글 조회를 완료했습니다"),
    COMMENT_READING_SUCCESS(HttpStatus.OK,"댓글 조회를 완료했습니다"),
    POST_LINK_READING_SUCCESS(HttpStatus.OK,"게시글 링크 조회를 완료했습니다"),
    POST_LIST_READING_SUCCESS(HttpStatus.OK,"게시글 목록 조회를 완료했습니다"),
    MY_POST_READING_SUCCESS(HttpStatus.OK,"내가 작성한 게시글 조회를 완료했습니다"),
    MY_COMMENT_READING_SUCCESS(HttpStatus.OK,"내가 작성한 댓글 조회를 완료했습니다"),
    USER_INFO_SUCCESS(HttpStatus.OK, "유저정보 불러오기 성공"),
    GOODS_DETAIL_SUCCESS(HttpStatus.OK, "상품 정보 불러오기 성공"),
    POST_SOFT_DELETE_SUCCESS(HttpStatus.OK,"게시글 임시삭제를 완료했습니다."),
    POST_DELETE_SUCCESS(HttpStatus.OK,"게시글 데이터 삭제를 완료했습니다."),
    COMMENT_DELETE_SUCCESS(HttpStatus.OK,"댓글 삭제를 완료했습니다."),
    POST_LINK_DELETE_SUCCESS(HttpStatus.OK,"게시글 링크 상호삭제를 완료했습니다."),
    Chat_List_SUCCESS(HttpStatus.OK, "채팅 내역 불러오기 성공"),
    Chat_Room_List_SUCCESS(HttpStatus.OK, "채팅방 불러오기 성공"),
    PET_INFO_SCRAP_DELETE_SUCCESS(HttpStatus.OK, "관심 유기동물 삭제 완료되었습니다."),
    PET_INFO_GET_DETAILS_SUCCESS(HttpStatus.OK, "유기동물 상세 페이지 불러오기 성공"),
    PET_INFO_GET_LIST_SUCCESS(HttpStatus.OK, "유기동물 전체 페이지 불러오기 성공"),
    PET_INFO_SCRAP_SUCCESS(HttpStatus.CREATED, "관심 유기동물 등록이 완료되었습니다."),
    PET_INFO_INQUIRY_SUCCESS(HttpStatus.CREATED, "해당 유기동물의 문의 내역이 저장되었습니다."),
    PUBLIC_PET_INFO_SAVE_SUCCESS(HttpStatus.CREATED, "유기동물 API 호출 및 DB 저장 성공."),
    POST_SCRAP_SUCCESS(HttpStatus.CREATED, "관심 유기동물 등록이 완료되었습니다."),
    DELETE_POST_SCRAP_SUCCESS(HttpStatus.CREATED, "관심 유기동물 등록이 완료되었습니다."),
    WITHDRAWAL_SUCCESS(HttpStatus.OK,"회원탈퇴에 성공하였습니다"),
    DECLARE_PUT_SUCCESS(HttpStatus.OK,"신고수정이 완료되었습니다"),
    DECLARE_DELETE_SUCCESS(HttpStatus.OK,"신고수정이 완료되었습니다"),
    DECLARE_SUCCESS(HttpStatus.OK,"신고가 완료되었습니다"),

    REISSUE_ACCESS_TOKEN(HttpStatus.OK, "Access 토큰 재발급 완료");
    private final HttpStatus httpStatus;
    private final String detail;
}
