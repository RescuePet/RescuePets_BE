package hanghae99.rescuepets.common.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class PostImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imageURL;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "petPostCatchId", nullable = true)
    private PetPostCatch petPostCatch;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "petPostMissingId", nullable = true)
    private PetPostMissing petPostMissing;
    @Builder
    public PostImage(PetPostCatch petPostCatch, String imageURL) {
        this.petPostCatch = petPostCatch;
        this.imageURL = imageURL;
    }
    public void setPostImage(PetPostCatch petPostCatch) {
        if(this.petPostCatch != null) {
            this.petPostCatch.getPostImages().remove(this);
        }
        this.petPostCatch = petPostCatch;
        if(!petPostCatch.getPostImages().contains(this)) {
            petPostCatch.addPostImage(this);
        }
    }
}
