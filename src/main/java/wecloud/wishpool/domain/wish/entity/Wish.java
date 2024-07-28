package wecloud.wishpool.domain.wish.entity;

import jakarta.persistence.*;
import lombok.*;
import wecloud.wishpool.domain.funding.entity.Funding;
import wecloud.wishpool.domain.review.entity.Review;
import wecloud.wishpool.domain.user.entity.User;
import wecloud.wishpool.global.auditing.BaseTimeEntity;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Wish extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wish_id")
    private Long id;
    private String title;
    private String content;
    private String image;
    private LocalDateTime deadline;
    private Long targetAmount;
    private Long currentAmount;
    private boolean isDeleted;

//    @OneToOne(mappedBy = "wish", fetch = FetchType.LAZY)
//    private Review review;

    @OneToMany(mappedBy = "wish")
    private List<Funding> fundings;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Wish(String title, String content, String image, LocalDateTime deadline, Long targetAmount, User user) {
        this.title = title;
        this.content = content;
        this.image = image;
        this.deadline = deadline;
        this.targetAmount = targetAmount;
        this.user = user;
    }

    public void update(String title, String content, String image, Long targetAmount) {
        this.title = title;
        this.content = content;
        this.image = image;
        this.targetAmount = targetAmount;
    }

    public void delete() {
        this.isDeleted = true;
    }
}
