package henrotaym.env.http.requests.relationships;

import henrotaym.env.annotations.ExistsInDatabase;
import henrotaym.env.repositories.UserRepository;
import java.math.BigInteger;

public record UserRelationshipRequest(
    @ExistsInDatabase(repository = UserRepository.class) BigInteger id) {}
