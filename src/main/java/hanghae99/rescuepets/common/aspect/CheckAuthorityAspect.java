package hanghae99.rescuepets.common.aspect;

import hanghae99.rescuepets.common.dto.CustomException;
import hanghae99.rescuepets.common.entity.MemberRoleEnum;
import hanghae99.rescuepets.common.security.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import static hanghae99.rescuepets.common.dto.ExceptionMessage.UNAUTHORIZED_ADMIN;
@Aspect
@Component
public class CheckAuthorityAspect {
    @Before("@annotation(hanghae99.rescuepets.common.annotation.CheckAuthority)")
    public void checkAuthority(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MemberDetails memberDetails = (MemberDetails) authentication.getPrincipal();
        MemberRoleEnum memberRoleEnum = memberDetails.getMember().getMemberRoleEnum();

        if (memberRoleEnum != MemberRoleEnum.ADMIN && memberRoleEnum != MemberRoleEnum.MANAGER) {
            throw new CustomException(UNAUTHORIZED_ADMIN);
        }
    }
}
