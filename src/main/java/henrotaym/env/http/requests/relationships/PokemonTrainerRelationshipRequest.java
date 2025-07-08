package henrotaym.env.http.requests.relationships;

import henrotaym.env.annotations.ExistsInDatabase;
import henrotaym.env.repositories.PokemonTrainerRepository;
import java.math.BigInteger;

public record PokemonTrainerRelationshipRequest(
    @ExistsInDatabase(repository = PokemonTrainerRepository.class) BigInteger id) {}
