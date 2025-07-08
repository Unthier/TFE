package henrotaym.env.repositories;

import henrotaym.env.entities.PokemonTrainer;
import java.math.BigInteger;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PokemonTrainerRepository extends JpaRepository<PokemonTrainer, BigInteger> {}
