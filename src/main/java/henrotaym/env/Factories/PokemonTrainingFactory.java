package henrotaym.env.Factories;

import henrotaym.env.entities.PokemonCatching;
import henrotaym.env.entities.PokemonTraining;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

@Component
public class PokemonTrainingFactory {
  public PokemonTraining create(PokemonCatching pokemonCatching) {
    PokemonTraining pokemonTraining = new PokemonTraining();
    pokemonTraining.setPokemonCatching(pokemonCatching);
    pokemonTraining.setTrainingOn(LocalDateTime.now());
    return pokemonTraining;
  }
}
