package henrotaym.env.repositories;

import henrotaym.env.entities.User;
import java.math.BigInteger;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, BigInteger> {
  Optional<User> findByName(String name);
}
