package henrotaym.env.mappers;

import org.springframework.stereotype.Component;

import henrotaym.env.entities.PokemonTrainer;
import henrotaym.env.entities.PokemonTraining;
import henrotaym.env.http.requests.relationships.PokemonTrainerRelationshipRequest;
import henrotaym.env.http.requests.relationships.PokemonTrainingRelationshipRequest;
import henrotaym.env.http.resources.PokemonTrainerResource;
import henrotaym.env.http.resources.PokemonTrainingResource;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class PokemonTrainingMapper {
    public PokemonTrainingResource resource(PokemonTraining PokemonTraining) {
        return new PokemonTrainingResource(
            PokemonTraining.getId(),
            PokemonTraining.getTrainingOn());
    }

  public PokemonTrainingRelationshipRequest relationshipRequest(PokemonTraining pokemonTraining) {
    return new PokemonTrainingRelationshipRequest(pokemonTraining.getId());
  }
}
