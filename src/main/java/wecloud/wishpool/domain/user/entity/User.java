package wecloud.wishpool.domain.user.entity;

import jakarta.persistence.*;
import lombok.*;
import wecloud.wishpool.domain.funding.entity.Funding;
import wecloud.wishpool.domain.reviewComment.entity.ReviewComment;
import wecloud.wishpool.domain.wish.entity.Wish;
import wecloud.wishpool.global.auditing.BaseTimeEntity;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class User extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    private String name;
    private String email;
    private String password;
    private LocalDate birth;
    private boolean isDeleted;

    @OneToMany(mappedBy = "user")
    private List<Wish> wishes;

    @OneToMany(mappedBy = "user")
    private List<Funding> fundings;

    @OneToMany(mappedBy = "user")
    private List<ReviewComment> reviewComments;

    @Builder
    public User(String name, String email, String password, LocalDate birth) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.birth = birth;
    }

}
