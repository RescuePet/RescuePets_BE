package hanghae99.rescuepets.common.entity;

import lombok.Getter;

@Getter
public enum PetState {
    NOTICE("공고중"),
    PROTECT("보호중"),
    END("종료");
    private final String korean;

    PetState(String korean) {
        this.korean = korean;
    }
}
