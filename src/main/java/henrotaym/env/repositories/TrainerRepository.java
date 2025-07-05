package henrotaym.env.repositories;

import henrotaym.env.entities.Trainer;
import java.math.BigInteger;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainerRepository extends JpaRepository<Trainer, BigInteger> {}
