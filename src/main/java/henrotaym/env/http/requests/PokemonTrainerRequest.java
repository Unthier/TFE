package henrotaym.env.http.requests;

import henrotaym.env.enums.PokemonTypeName;
import henrotaym.env.http.requests.relationships.TrainerRelationshipRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.math.BigInteger;

public record PokemonTrainerRequest(
    @NotNull BigInteger pokemonId,
    @NotNull String name,
    @NotNull Integer attack,
    @NotNull Integer speed,
    @NotNull Integer pv,
    @NotNull Integer defense,
    @NotNull Integer damage,
    @NotNull Integer level,
    @NotNull PokemonTypeName type,
    @Valid TrainerRelationshipRequest trainer) {}
