package henrotaym.env.http.requests.relationships;

import henrotaym.env.annotations.ExistsInDatabase;
import henrotaym.env.repositories.PokemonCatchingRepository;
import java.math.BigInteger;

public record PokemonCatchingRelationshipRequest(
    @ExistsInDatabase(repository = PokemonCatchingRepository.class) BigInteger id) {}
