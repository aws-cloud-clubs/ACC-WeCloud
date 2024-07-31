package wecloud.wishpool.domain.review.entity;

import jakarta.persistence.*;
import lombok.*;
import wecloud.wishpool.domain.reviewComment.entity.ReviewComment;
import wecloud.wishpool.domain.wish.entity.Wish;
import wecloud.wishpool.global.auditing.BaseTimeEntity;

import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;
    private String content;
    private String image;
    private boolean isDeleted;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wish_id")
    private Wish wish;

    @OneToMany(mappedBy = "review")
    private List<ReviewComment> reviewComments;

    @Builder
    public Review(String content, String image, Wish wish) {
        this.content = content;
        this.image = image;
        this.wish = wish;
    }
}
