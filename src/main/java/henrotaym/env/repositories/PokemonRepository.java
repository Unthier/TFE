package henrotaym.env.repositories;

import henrotaym.env.entities.Pokemon;
import java.math.BigInteger;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PokemonRepository extends JpaRepository<Pokemon, BigInteger> {}
