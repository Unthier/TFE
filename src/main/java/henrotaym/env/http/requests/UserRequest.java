package henrotaym.env.http.requests;

import henrotaym.env.enums.UserRoleName;
import henrotaym.env.http.requests.relationships.PokemonCatchingRelationshipRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record UserRequest(
    @NotBlank String name,
    @Valid String password,
    @NotNull UserRoleName role,
    @NotNull String mail,
    @Valid List<PokemonCatchingRelationshipRequest> pokemonsCatching) {}
