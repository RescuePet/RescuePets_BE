package hanghae99.rescuepets.common.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
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

//    @Builder
//    public PostImage(PetPostCatch petPostCatch, String imageURL) {
//        this.petPostCatch = petPostCatch;
//        this.imageURL = imageURL;
//    }
//    @Builder
//    public PostImage(PetPostMissing petPostMissing, String imageURL) {
//        this.petPostMissing = petPostMissing;
//        this.imageURL = imageURL;
//    }
    public PostImage(PetPostCatch petPostCatch, String imageURL) {
        this.petPostCatch = petPostCatch;
        this.imageURL = imageURL;
    }
    public PostImage(PetPostMissing petPostMissing, String imageURL) {
        this.petPostMissing = petPostMissing;
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
    public void setPostImage(PetPostMissing petPostMissing) {
        if(this.petPostMissing != null) {
            this.petPostMissing.getPostImages().remove(this);
        }
        this.petPostMissing = petPostMissing;
        if(!petPostMissing.getPostImages().contains(this)) {
            petPostMissing.addPostImage(this);
        }
    }
}
