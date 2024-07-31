package wecloud.wishpool.domain.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wecloud.wishpool.domain.review.entity.Review;

public interface ReviewRepository extends JpaRepository<Review,Long> {
}
