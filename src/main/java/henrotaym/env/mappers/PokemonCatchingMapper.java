package henrotaym.env.mappers;

import henrotaym.env.entities.PokemonCatching;
import henrotaym.env.http.requests.relationships.PokemonCatchingRelationshipRequest;
import henrotaym.env.http.resources.PokemonCatchingResource;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PokemonCatchingMapper {
  public PokemonCatchingResource resource(PokemonCatching pokemonCatching) {
    return new PokemonCatchingResource(
        pokemonCatching.getId(),
        pokemonCatching.getPokemonId(),
        pokemonCatching.getName(),
        pokemonCatching.getNickname(),
        pokemonCatching.getAttack(),
        pokemonCatching.getSpeed(),
        pokemonCatching.getPv(),
        pokemonCatching.getDefense(),
        pokemonCatching.getLevel(),
        pokemonCatching.getNameOfNextEvolution(),
        pokemonCatching.getLevelOfNextEvolution(),
        pokemonCatching.getType(),
        pokemonCatching.getCatchingOn(),
        pokemonCatching.getStatus(),
        pokemonCatching.getDamage());
  }

  public PokemonCatchingRelationshipRequest relationshipRequest(PokemonCatching pokemonCatching) {
    return new PokemonCatchingRelationshipRequest(pokemonCatching.getId());
  }
}
