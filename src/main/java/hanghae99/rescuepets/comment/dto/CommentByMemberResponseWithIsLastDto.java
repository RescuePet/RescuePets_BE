package hanghae99.rescuepets.comment.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class CommentByMemberResponseWithIsLastDto {
    private List<CommentByMemberResponseDto> DtoList = new ArrayList<>();
    private Boolean isLast = false;
//    public CommentByMemberResponseWithIsLastDto(List<CommentByMemberResponseDto> DtoList, Boolean isLast){
//        this.
//    }
    @Builder
    public static CommentByMemberResponseWithIsLastDto of(List<CommentByMemberResponseDto> DtoList, Boolean isLast) {
        return CommentByMemberResponseWithIsLastDto.builder()
                .DtoList(DtoList)
                .isLast(isLast)
                .build();
    }

}
