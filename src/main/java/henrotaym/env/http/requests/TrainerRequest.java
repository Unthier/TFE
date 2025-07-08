package henrotaym.env.http.requests;

import henrotaym.env.http.requests.relationships.PokemonTrainerRelationshipRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java.util.List;

public record TrainerRequest(
    @NotBlank String name, @Valid List<PokemonTrainerRelationshipRequest> pokemonsTrainer) {}
