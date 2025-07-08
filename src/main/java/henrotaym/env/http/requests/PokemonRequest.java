package henrotaym.env.http.requests;

import henrotaym.env.enums.PokemonTypeName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PokemonRequest(
    @NotBlank String name,
    String nickname,
    @NotNull Integer attack,
    @NotNull Integer speed,
    @NotNull Integer pv,
    @NotNull Integer defense,
    @NotNull Integer captureRate,
    @NotNull PokemonTypeName type) {}
