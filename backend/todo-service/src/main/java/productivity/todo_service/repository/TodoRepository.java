package productivity.todo_service.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import productivity.todo_service.entity.Todo;

public interface TodoRepository extends JpaRepository<Todo, Long> {
  List<Todo> findByUsername(String username);
}
