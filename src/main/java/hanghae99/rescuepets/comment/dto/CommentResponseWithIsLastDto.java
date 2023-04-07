package hanghae99.rescuepets.comment.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
public class CommentResponseWithIsLastDto {
    private List<CommentResponseDto> DtoList = new ArrayList<>();
    private Boolean isLast = false;
    public CommentResponseWithIsLastDto(List<CommentResponseDto> DtoList, Boolean isLast){
        this.DtoList = DtoList;
        this.isLast = isLast;
    }
    public static CommentResponseWithIsLastDto of(List<CommentResponseDto> DtoList, Boolean isLast) {
        return CommentResponseWithIsLastDto.builder()
                .DtoList(DtoList)
                .isLast(isLast)
                .build();
    }
}
