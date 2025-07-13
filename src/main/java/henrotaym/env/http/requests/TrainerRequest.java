package henrotaym.env.http.requests;

import henrotaym.env.enums.TrainerStatusName;
import henrotaym.env.http.requests.relationships.PokemonTrainerRelationshipRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record TrainerRequest(
    @NotBlank String name,
    @NotNull TrainerStatusName status,
    @Valid List<PokemonTrainerRelationshipRequest> pokemonsTrainer) {}
