package henrotaym.env.Factories;

import henrotaym.env.entities.Pokemon;
import henrotaym.env.entities.PokemonCatching;
import henrotaym.env.enums.PokemonCatchingStatusName;
import henrotaym.env.enums.PokemonTypeName;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

@Component
public class PokemonCatchingFactory {
  public PokemonCatching create(Pokemon pokemon) {
    PokemonCatching pokemonCatching = new PokemonCatching();
    pokemonCatching.setAttack(pokemon.getAttack());
    pokemonCatching.setSpeed(pokemon.getSpeed());
    pokemonCatching.setCatchingOn(LocalDateTime.now());
    pokemonCatching.setPokemonId(pokemon.getId());
    pokemonCatching.setDefense(pokemon.getDefense());
    pokemonCatching.setLevel(1);
    pokemonCatching.setPv(pokemon.getPv());
    pokemonCatching.setName(pokemon.getName());
    pokemonCatching.setNickname(null);
    pokemonCatching.setStatus(PokemonCatchingStatusName.NEUTRE);
    pokemonCatching.setType(pokemon.getType());
    pokemonCatching.setDamage(0);
    pokemonCatching.setNameOfNextEvolution(pokemon.getNameOfNextEvolution());
    pokemonCatching.setLevelOfNextEvolution(pokemon.getNextEvolutionLevel());
    return pokemonCatching;
  }

  public PokemonCatching createEgg() {
    PokemonCatching pokemonCatching = new PokemonCatching();
    pokemonCatching.setCatchingOn(LocalDateTime.now());
    pokemonCatching.setAttack(0);
    pokemonCatching.setSpeed(0);
    pokemonCatching.setDefense(0);
    pokemonCatching.setPv(0);
    pokemonCatching.setLevel(0);
    pokemonCatching.setType(PokemonTypeName.EGG);
    pokemonCatching.setStatus(PokemonCatchingStatusName.EGG);
    pokemonCatching.setDamage(0);
    pokemonCatching.setNameOfNextEvolution(null);
    pokemonCatching.setLevelOfNextEvolution(null);
    return pokemonCatching;
  }

  public PokemonCatching update(PokemonCatching pokemonCatching, Pokemon pokemon) {
    pokemonCatching.setId(pokemonCatching.getId());
    pokemonCatching.setPokemonId(pokemon.getId());
    pokemonCatching.setCatchingOn(pokemonCatching.getCatchingOn());
    pokemonCatching.setAttack(pokemon.getAttack());
    pokemonCatching.setSpeed(pokemon.getSpeed());
    pokemonCatching.setDefense(pokemon.getDefense());
    pokemonCatching.setPv(pokemon.getPv());
    pokemonCatching.setName(pokemon.getName());
    pokemonCatching.setType(pokemon.getType());
    pokemonCatching.setLevel(pokemonCatching.getLevel() + 1);
    pokemonCatching.setNameOfNextEvolution(pokemon.getNameOfNextEvolution());
    pokemonCatching.setLevelOfNextEvolution(pokemon.getNextEvolutionLevel());
    return pokemonCatching;
  }
}
