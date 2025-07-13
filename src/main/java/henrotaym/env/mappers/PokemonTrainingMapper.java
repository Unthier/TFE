package henrotaym.env.mappers;

import henrotaym.env.entities.PokemonTraining;
import henrotaym.env.http.requests.relationships.PokemonTrainingRelationshipRequest;
import henrotaym.env.http.resources.PokemonTrainingResource;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PokemonTrainingMapper {
  public PokemonTrainingResource resource(PokemonTraining PokemonTraining) {
    return new PokemonTrainingResource(PokemonTraining.getId(), PokemonTraining.getTrainingOn());
  }

  public PokemonTrainingRelationshipRequest relationshipRequest(PokemonTraining pokemonTraining) {
    return new PokemonTrainingRelationshipRequest(pokemonTraining.getId());
  }
}
