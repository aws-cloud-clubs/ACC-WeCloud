package wecloud.wishpool.domain.reviewComment.entity;

import jakarta.persistence.*;
import lombok.*;
import wecloud.wishpool.domain.review.entity.Review;
import wecloud.wishpool.domain.user.entity.User;
import wecloud.wishpool.global.auditing.BaseTimeEntity;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewComment extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_comment_id")
    private Long id;
    private String content;
    private boolean isDeleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public ReviewComment(String content, Review review, User user) {
        this.content = content;
        this.review = review;
        this.user = user;
    }
}
