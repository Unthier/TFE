package henrotaym.env.repositories;

import henrotaym.env.entities.PokemonCatching;
import java.math.BigInteger;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PokemonCatchingRepository extends JpaRepository<PokemonCatching, BigInteger> {}
