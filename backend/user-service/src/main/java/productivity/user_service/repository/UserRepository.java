package productivity.user_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import productivity.user_service.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
  boolean existsByUsername(String username);

  User findByUsername(String username);
}
