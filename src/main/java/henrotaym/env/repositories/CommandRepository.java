package henrotaym.env.repositories;

import henrotaym.env.entities.Command;
import java.math.BigInteger;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommandRepository extends JpaRepository<Command, BigInteger> {}
