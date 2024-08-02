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
    @Column(nullable = false)
    private Long currentAmount = 0L;
    @Column(nullable = false)
    private boolean isCompleted = false; ; // 펀딩상태
    @Column(nullable = false)
    private boolean isEnded = false; ; // 마감여부
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

    public void updateImage(String image) {
        this.image = image;
    }
    public void addCurrentAmount(Long amount) {
        if (this.currentAmount == null) {
            this.currentAmount = 0L;  // 현재 금액이 null일 경우 초기화
        }
        this.currentAmount += amount;
        if (this.currentAmount >= this.targetAmount) { // 목표금액 달성 시 펀딩 완료
            updateEnd();
            updateCompleted();
        }
    }

    public void updateEnd() {
        this.isEnded = true;
    }

    public void updateCompleted() {
        this.isCompleted = true;
    }

}
