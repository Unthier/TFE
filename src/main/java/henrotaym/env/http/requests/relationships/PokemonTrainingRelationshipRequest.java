package henrotaym.env.http.requests.relationships;

import java.math.BigInteger;

import henrotaym.env.annotations.ExistsInDatabase;
import henrotaym.env.repositories.PokemonTrainerRepository;

public record PokemonTrainingRelationshipRequest(
    @ExistsInDatabase(repository = PokemonTrainerRepository.class) BigInteger id
) {
}
