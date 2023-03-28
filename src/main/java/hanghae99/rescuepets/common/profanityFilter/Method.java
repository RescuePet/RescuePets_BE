package hanghae99.rescuepets.common.profanityFilter;

import java.util.*;
public interface Method {
    void add(String...texts);
    void add(List<String> texts);
    void add(Set<String> texts);
    void remove(String...texts);
    void remove(List<String> texts);
    void remove(Set<String> texts);
}
