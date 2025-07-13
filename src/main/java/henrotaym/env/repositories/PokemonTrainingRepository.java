package henrotaym.env.repositories;

import henrotaym.env.entities.PokemonTraining;
import java.math.BigInteger;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PokemonTrainingRepository extends JpaRepository<PokemonTraining, BigInteger> {}
