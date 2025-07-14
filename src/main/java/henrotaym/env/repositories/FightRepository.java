package henrotaym.env.repositories;

import henrotaym.env.entities.Fight;
import java.math.BigInteger;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FightRepository extends JpaRepository<Fight, BigInteger> {}
