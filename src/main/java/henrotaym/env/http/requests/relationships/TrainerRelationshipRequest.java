package henrotaym.env.http.requests.relationships;

import henrotaym.env.annotations.ExistsInDatabase;
import henrotaym.env.repositories.TrainerRepository;
import java.math.BigInteger;

public record TrainerRelationshipRequest(
    @ExistsInDatabase(repository = TrainerRepository.class) BigInteger id) {}
