package hanghae99.rescuepets.common.profanityFilter;

import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ProfanityFiltering implements Word, Method{

    private final Set<String> set = new HashSet<>(List.of(badWords));
    private String substituteValue = "*";

    //대체 문자 지정
    //기본값 : *
    public ProfanityFiltering(String substituteValue) {
        this.substituteValue = substituteValue;
    }
    public ProfanityFiltering() {}

    //특정 문자 추가, 삭제
    @Override
    public void add(String...texts) {
        set.addAll(List.of(texts));
    }

    @Override
    public void add(List<String> texts) {
        set.addAll(texts);
    }

    @Override
    public void add(Set<String> texts) {
        set.addAll(texts);
    }

    @Override
    public void remove(String...texts) {
        List.of(texts).forEach(set::remove);
    }

    @Override
    public void remove(List<String> texts) {
        texts.forEach(set::remove);
    }

    @Override
    public void remove(Set<String> texts) {
        texts.forEach(set::remove);
    }

    //비속어 있다면 대체
    public String checkAndChange(String text) {
        Set<String> s = set.stream()
                .filter(text::contains)
                .collect(Collectors.toSet());

        for (String v : s) {
            int textLen = v.length();
            String sub = this.substituteValue.repeat(textLen);
            text = text.replace(v, sub);
        }
        return text;
    }
    //비속어가 1개라도 존재하면 true 반환
    public boolean check(String text) {
        return set.stream()
                .anyMatch(text::contains);
    }

    //공백을 없는 상태 체크
    public boolean blankCheck(String text) {
        String cpText = text
                .replace(" ", "")
                .replace("_", "")
                .replace("-", "")
                .replace(",", "")
                .replace(".", "");
        return check(cpText);
    }
}
