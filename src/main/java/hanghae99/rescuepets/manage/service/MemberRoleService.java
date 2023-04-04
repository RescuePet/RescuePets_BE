package hanghae99.rescuepets.manage.service;

import hanghae99.rescuepets.common.dto.CustomException;
import hanghae99.rescuepets.common.dto.ResponseDto;
import hanghae99.rescuepets.common.entity.Member;
import hanghae99.rescuepets.common.entity.PetPostCatch;
import hanghae99.rescuepets.manage.dto.MemberRequestDto;
import hanghae99.rescuepets.member.repository.MemberRepository;
import hanghae99.rescuepets.memberpet.dto.PetPostCatchResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static hanghae99.rescuepets.common.dto.ExceptionMessage.*;
import static hanghae99.rescuepets.common.dto.SuccessMessage.POST_LIST_READING_SUCCESS;
import static hanghae99.rescuepets.common.dto.SuccessMessage.USER_ENUM_CHANGE_SUCCESS;
import static hanghae99.rescuepets.common.entity.MemberRoleEnum.ADMIN;
import static hanghae99.rescuepets.common.entity.MemberRoleEnum.MANAGER;

@Service
@RequiredArgsConstructor
public class MemberRoleService {
    private final MemberRepository memberRepository;

    @Transactional
    public ResponseEntity<ResponseDto> changeEnum(Long memberId, MemberRequestDto requestDto, Member member) {
        Member memberTemp = memberRepository.findById(memberId).orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));
        if(member.getMemberRole() == MANAGER||member.getMemberRole() == ADMIN){
            memberTemp.setEnum(requestDto.getMemberRole());
        }else{
            throw new CustomException(UNAUTHORIZED_UPDATE_OR_DELETE);
        }
        return ResponseDto.toResponseEntity(USER_ENUM_CHANGE_SUCCESS);
    }
}
