package henrotaym.env.http.requests.relationships;

import henrotaym.env.annotations.ExistsInDatabase;
import henrotaym.env.repositories.FightRepository;
import java.math.BigInteger;

public record FightRelationshipRequest(
    @ExistsInDatabase(repository = FightRepository.class) BigInteger id) {}
