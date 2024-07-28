package wecloud.wishpool.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wecloud.wishpool.domain.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
