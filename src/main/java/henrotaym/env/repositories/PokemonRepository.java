package henrotaym.env.repositories;

import henrotaym.env.entities.Pokemon;
import java.math.BigInteger;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PokemonRepository extends JpaRepository<Pokemon, BigInteger> {
  @Query("SELECT p.id FROM Pokemon p")
  List<BigInteger> findAllIds();
}
