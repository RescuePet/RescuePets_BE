package hanghae99.rescuepets.common.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class PostLink {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
