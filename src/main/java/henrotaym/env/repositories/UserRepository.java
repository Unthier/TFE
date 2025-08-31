package henrotaym.env.repositories;

import java.math.BigInteger;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import henrotaym.env.entities.User;

public interface UserRepository extends JpaRepository<User, BigInteger> {
  Optional<User> findByName(String name);

  Optional<User> findByMail(String email);
}
