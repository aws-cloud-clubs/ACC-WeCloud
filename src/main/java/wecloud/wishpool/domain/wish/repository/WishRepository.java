package wecloud.wishpool.domain.wish.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wecloud.wishpool.domain.wish.entity.Wish;

import java.util.List;
import java.util.Optional;

public interface WishRepository extends JpaRepository<Wish,Long> {
    Optional<Wish> findByIdAndIsDeletedFalse(Long id);
    List<Wish> findByIsCompletedTrueOrIsEndedTrue();
}
