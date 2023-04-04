package hanghae99.rescuepets.common.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
    private Post post;
    public PostImage(Post post, String imageURL) {
        this.post = post;
        this.imageURL = imageURL;
    }
    public void setPostImage(Post post) {
        if(this.post != null) {
            this.post.getPostImages().remove(this);
        }
        this.post = post;
        if(!post.getPostImages().contains(this)) {
            post.addPostImage(this);
        }
    }
}
