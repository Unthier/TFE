package henrotaym.env.http.requests.relationships;

import henrotaym.env.annotations.ExistsInDatabase;
import henrotaym.env.repositories.PokemonTrainingRepository;
import java.math.BigInteger;

public record PokemonTrainingRelationshipRequest(
    @ExistsInDatabase(repository = PokemonTrainingRepository.class) BigInteger id) {}
