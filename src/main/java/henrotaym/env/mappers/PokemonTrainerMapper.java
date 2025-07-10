package henrotaym.env.mappers;

import henrotaym.env.entities.PokemonTrainer;
import henrotaym.env.http.requests.relationships.PokemonTrainerRelationshipRequest;
import henrotaym.env.http.resources.PokemonTrainerResource;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PokemonTrainerMapper {
  public PokemonTrainerResource resource(PokemonTrainer pokemonTrainer) {
    return new PokemonTrainerResource(
        pokemonTrainer.getId(),
        pokemonTrainer.getPokemonId(),
        pokemonTrainer.getName(),
        pokemonTrainer.getAttack(),
        pokemonTrainer.getSpeed(),
        pokemonTrainer.getPv(),
        pokemonTrainer.getDefense(),
        pokemonTrainer.getLevel(),
        pokemonTrainer.getType(),
        pokemonTrainer.getDamage());
  }

  public PokemonTrainerRelationshipRequest relationshipRequest(PokemonTrainer pokemonTrainer) {
    return new PokemonTrainerRelationshipRequest(pokemonTrainer.getId());
  }
}
