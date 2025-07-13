package henrotaym.env.mappers;

import henrotaym.env.entities.PokemonTrainer;
import henrotaym.env.http.requests.PokemonTrainerRequest;
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

  public PokemonTrainer request(PokemonTrainerRequest request, PokemonTrainer pokemonTrainer) {
    pokemonTrainer.setName(request.name());
    pokemonTrainer.setPokemonId(request.pokemonId());
    pokemonTrainer.setDamage(request.damage());
    pokemonTrainer.setLevel(request.level());
    pokemonTrainer.setAttack(request.attack());
    pokemonTrainer.setDefense(request.defense());
    pokemonTrainer.setPv(request.pv());
    pokemonTrainer.setSpeed(request.speed());
    pokemonTrainer.setType(request.type());

    return pokemonTrainer;
  }

  public PokemonTrainerRelationshipRequest relationshipRequest(PokemonTrainer pokemonTrainer) {
    return new PokemonTrainerRelationshipRequest(pokemonTrainer.getId());
  }
}
