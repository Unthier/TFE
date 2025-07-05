package henrotaym.env.repositories;

import henrotaym.env.entities.User;
import java.math.BigInteger;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, BigInteger> {}
