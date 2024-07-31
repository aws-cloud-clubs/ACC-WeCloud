package wecloud.wishpool.domain.funding.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wecloud.wishpool.domain.funding.entity.Funding;

import java.util.List;

public interface FundingRepository extends JpaRepository<Funding,Long> {
    List<Funding> findByWishId(Long wishId);
}
