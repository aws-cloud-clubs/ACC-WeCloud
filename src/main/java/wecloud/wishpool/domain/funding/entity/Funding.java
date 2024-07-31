package wecloud.wishpool.domain.funding.entity;

import jakarta.persistence.*;
import lombok.*;
import wecloud.wishpool.domain.user.entity.User;
import wecloud.wishpool.domain.wish.entity.Wish;
import wecloud.wishpool.global.auditing.BaseTimeEntity;

import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Funding extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "funding_id")
    private Long id;
    private Long amount;
    private String message;
    private boolean isDeleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wish_id")
    private Wish wish;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Funding(Long amount, String message, Wish wish, User user) {
        this.amount = amount;
        this.message = message;
        this.wish = wish;
        this.user = user;
    }
}
