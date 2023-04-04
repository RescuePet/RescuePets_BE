package hanghae99.rescuepets.manage.dto;

import hanghae99.rescuepets.common.entity.MemberRoleEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
public class MemberRequestDto {


    @Column(nullable = false)
    private MemberRoleEnum memberRole;
}
