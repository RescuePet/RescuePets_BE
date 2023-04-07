package hanghae99.rescuepets.comment.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
public class CommentByMemberResponseWithIsLastDto {
    private List<CommentByMemberResponseDto> DtoList = new ArrayList<>();
    private Boolean isLast = false;
    public CommentByMemberResponseWithIsLastDto(List<CommentByMemberResponseDto> DtoList, Boolean isLast){
        this.DtoList = DtoList;
        this.isLast = isLast;
    }
    public static CommentByMemberResponseWithIsLastDto of(List<CommentByMemberResponseDto> DtoList, Boolean isLast) {
        return CommentByMemberResponseWithIsLastDto.builder()
                .DtoList(DtoList)
                .isLast(isLast)
                .build();
    }
}
